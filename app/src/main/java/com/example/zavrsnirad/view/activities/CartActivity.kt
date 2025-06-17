package com.example.zavrsnirad.view.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zavrsnirad.databinding.ActivityCartBinding
import com.example.zavrsnirad.databinding.ItemCheckoutBinding
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.utils.DataEncryption
import com.example.zavrsnirad.utils.OnClick
import com.example.zavrsnirad.view.activities.MainActivity.Companion.cartList
import com.example.zavrsnirad.view.adapter.ItemsAdapter
import com.example.zavrsnirad.viewmodel.GetOrderTokenViewModel
import com.example.zavrsnirad.viewmodel.GetOrderTokenViewModelFactory
import com.example.zavrsnirad.repository.TransactionRepository
import com.example.zavrsnirad.viewmodel.TransactionViewModel
import com.example.zavrsnirad.viewmodel.TransactionViewModelFactory
import com.revolut.cardpayments.api.RevolutPaymentApi
import com.revolut.cardpayments.core.api.AddressParams

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var getOrderTokenViewModel: GetOrderTokenViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var databaseHelper: DatabaseHelper
    private var totalAmount: Int = 0
    var encryptedName = ""
    var encryptedEmail = ""
    var encryptedPhone = ""
    var encryptedDeliveryAddress = ""
    var encryptedOrderToken = ""
    var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val getOrderFactory = GetOrderTokenViewModelFactory(databaseHelper)
        getOrderTokenViewModel = ViewModelProvider(this, getOrderFactory)[GetOrderTokenViewModel::class.java]

        val transactionRepository = TransactionRepository(databaseHelper)
        val transactionFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(this, transactionFactory)[TransactionViewModel::class.java]

        // Klik na gumb "nazad" zatvara aktivnost
        binding.ivBack.setOnClickListener { finish() }

        //  Klik na gumb "Placanje" inicijalizira proces placanja
        binding.btnCheckout.setOnClickListener {
            showCheckoutDialog()
        }

        // Pracenje promjena u ViewModel-u
        observeViewModel()
    }

    private fun showCheckoutDialog() {
        val dialog = Dialog(this)
        val bindingDialog = ItemCheckoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)

        // Postavljanje dijalog prozora sa sirinom na match_parent i transparentnim fontom
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Dodavanje 10dp margine
        val params = bindingDialog.root.layoutParams as ViewGroup.MarginLayoutParams
        val marginInPixels = (10 * resources.displayMetrics.density).toInt() // Pretvorba dp u pixele
        params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels)
        bindingDialog.root.layoutParams = params

        // Dodavanje slusaca za klik na gumb potvrdi
        bindingDialog.btnConfirm.setOnClickListener {
            val name = bindingDialog.etName.text.toString().trim()
            val email = bindingDialog.etEmail.text.toString().trim()
            val phone = bindingDialog.etPhone.text.toString().trim()
            val deliveryAddress = bindingDialog.etPhone.text.toString().trim()

            if (validateInput(name, email, phone, deliveryAddress)) {
                encryptedName = DataEncryption.encryptData(name)
                encryptedEmail = DataEncryption.encryptData(email)
                encryptedPhone = DataEncryption.encryptData(phone)
                encryptedDeliveryAddress = DataEncryption.encryptData(deliveryAddress)
                dialog.dismiss()
                getOrderTokenViewModel.initiatePayment(totalAmount)
            }
        }

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun validateInput(name : String, email: String, phone: String, deliveryAddress: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "Molimo unesite ime", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Molimo unesite ispravan email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.isEmpty() || phone.length < 10) {
            Toast.makeText(this, "Molimo unesite ispravan broj telefona", Toast.LENGTH_SHORT).show()
            return false
        }

        if (deliveryAddress.isEmpty()) {
            Toast.makeText(this, "Molimo unesite adresu dostave", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()

        // Provjera sadrzi li kosarica proizvode
        if (cartList.isNotEmpty()) {
            // Izracun ukupne cijene proizvoda u kosarici
            totalAmount = cartList.sumOf { it.price }
            binding.tvTotal.text = "€$totalAmount" // Update total amount texta

            // Prikaz donjeg layouta i recycler view-a
            binding.llBottom.visibility = View.VISIBLE
            binding.rvItems.visibility = View.VISIBLE

            // Postavljanje RecyclerViewa sa LinearLayoutManagerom i custom adapterom
            binding.rvItems.layoutManager = LinearLayoutManager(this)
            itemsAdapter = ItemsAdapter(cartList, object : OnClick {
                // Definiranje slucaja klik na proizvod u kosarici za navigaciju na detalje proizvoda
                override fun clicked(pos: Int) {
                    val shopModel = cartList[pos]
                    val intent = Intent(this@CartActivity, ProductDetailsActivity::class.java)
                    intent.putExtra("from", "cart")
                    intent.putExtra("data", shopModel)
                    startActivity(intent)
                }
            })
            binding.rvItems.adapter = itemsAdapter
        } else {
            // Sakrivanje donjeg layouta i recycler view ako je kosarica prazna
            binding.llBottom.visibility = View.GONE
            binding.rvItems.visibility = View.GONE
        }
    }

    // Promatranje ViewModel-a za rezultat placanja i ažuriranje UI-a
    private fun observeViewModel() {
        getOrderTokenViewModel.paymentResult.observe(this) { paymentResponse ->
            encryptedOrderToken = ""
            paymentResponse?.let {
                Log.d("Placanje", "Payment ID: ${it.id}, Token: ${it.token}")
                encryptedOrderToken = DataEncryption.encryptData(it.token)
                if (it.token != null) {
                    val decryptedEmail = DataEncryption.decryptData(encryptedEmail)
                    val decryptedAddress = DataEncryption.decryptData(encryptedDeliveryAddress)
                    // Pokretanje Revolut Payment API-a ua placanje karticom
                    startActivityForResult(
                        RevolutPaymentApi.buildCardPaymentIntent(
                            context = this,
                            orderId = DataEncryption.decryptData(encryptedOrderToken),
                            RevolutPaymentApi.Configuration.CardPayment(
                                email = decryptedEmail,
                                billingAddress = AddressParams(
                                    streetLine1 = decryptedAddress,
                                    streetLine2 = null,
                                    city = "Zagreb",
                                    region = null,
                                    country = "HR",
                                    postcode = "10040"
                                ),
                                shippingAddress = null,
                                savePaymentMethodFor = null
                            )
                        ), 0
                    )
                } else {
                    // Prikazivanje toasta ako je placanje uspjesno ali nema tokena
                    Toast.makeText(
                        this,
                        "Plaćanje uspješno, ali order token nije pronađen.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } ?: run {
                // Prikazivanje toasta ako je placanje neuspjesno
                Toast.makeText(this, "Plaćanje nije uspjelo", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Mapiranje rezultata od Revolut API-a
        val result = RevolutPaymentApi.mapIntentToResult(resultCode, data)

        if (result != null) {
            status = ""
            when (result) {
                // Rukovanje uspjesnim placanjem
                is com.revolut.cardpayments.api.Result.Authorised -> {
                    status = "Payment Successful!"
                    Toast.makeText(this, "Plaćanje uspješno!", Toast.LENGTH_LONG).show()
                    cartList.clear() // ciscenje kosarice
                    finish() // zatvaranje aktivnosti
                    Log.d("Plaćanje", "Placanje uspjesno.")
                }

                // Rukovanje neuspjelim placanjem
                is com.revolut.cardpayments.api.Result.Failed -> {
                    status = "Payment Failed: ${result.failureReason.name}"
                    Toast.makeText(
                        this, "Plaćanje neuspješno: ${result.failureReason.name}", Toast.LENGTH_LONG
                    ).show()
                    Log.e("Placanje", "Payment failed: ${result.failureReason.name}")
                }

                // Ako korisnik odbije placanje
                is com.revolut.cardpayments.api.Result.Declined -> {
                    status = "Payment declined."
                    Toast.makeText(this, "Plaćanje odbijeno", Toast.LENGTH_LONG).show()
                    Log.d("Placanje", "Placanje odbijeno.")
                }

                // Ostali slucajevi
                else -> {}
            }

            if (status.isNotEmpty()){
                val transactionModel = TransactionModel(
                    name = encryptedName,
                    email = encryptedEmail,
                    phone = encryptedPhone,
                    deliveryAddress = encryptedDeliveryAddress,
                    totalAmount = totalAmount.toString(),
                    orderToken = encryptedOrderToken,
                    status = status
                )
                transactionViewModel.insertTransaction(transactionModel)
            }

        } else {
            // Nepoznata greska
            Toast.makeText(this, "Dogodila se nepoznata greška.", Toast.LENGTH_LONG).show()
            Log.e("Placanje", "Nepoznata greska ili rezultat null.")
        }
    }
}
