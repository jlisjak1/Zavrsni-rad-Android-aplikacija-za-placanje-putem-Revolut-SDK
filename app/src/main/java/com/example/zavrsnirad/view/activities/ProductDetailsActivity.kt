package com.example.zavrsnirad.view.activities

import android.annotation.SuppressLint
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
import com.example.zavrsnirad.databinding.ActivityProductDetailsBinding
import com.example.zavrsnirad.databinding.ItemCheckoutBinding
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.utils.DataEncryption
import com.example.zavrsnirad.view.activities.MainActivity.Companion.cartList
import com.example.zavrsnirad.viewmodel.GetOrderTokenViewModel
import com.example.zavrsnirad.viewmodel.GetOrderTokenViewModelFactory
import com.example.zavrsnirad.repository.TransactionRepository
import com.example.zavrsnirad.viewmodel.TransactionViewModel
import com.example.zavrsnirad.viewmodel.TransactionViewModelFactory
import com.revolut.cardpayments.api.RevolutPaymentApi
import com.revolut.cardpayments.core.api.AddressParams

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var getOrderTokenViewModel: GetOrderTokenViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var databaseHelper: DatabaseHelper
    private var from = ""
    private var productModel: ProductModel? = null
    var encryptedName = ""
    var encryptedEmail = ""
    var encryptedPhone = ""
    var encryptedDeliveryAddress = ""
    var encryptedOrderToken = ""
    var status = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val getOrderFactory = GetOrderTokenViewModelFactory(databaseHelper)
        getOrderTokenViewModel = ViewModelProvider(this, getOrderFactory)[GetOrderTokenViewModel::class.java]

        val transactionRepository = TransactionRepository(databaseHelper)
        val transactionFactory = TransactionViewModelFactory(transactionRepository)
        transactionViewModel = ViewModelProvider(this, transactionFactory)[TransactionViewModel::class.java]

        // Dohvacanje podataka od intenta
        if (intent.extras != null) {
            from = intent.getStringExtra("from").toString()
            productModel = intent.getSerializableExtra("data") as ProductModel?
        }

        // Postavljanje texta gumba i vidljivosti ako je aktivnost otvorena iz kosarice
        if (from == "cart") {
            binding.btnAddToCart.text = "Ukloni iz košarice"
            binding.btnBuy.visibility = View.GONE
        }

        // Klik na gumb "nazad"
        binding.ivBack.setOnClickListener { finish() }

        // Prikazivanje detalja proizvoda
        productModel?.let {
            binding.ivImage.setImageResource(it.image)
            binding.tvName.text = "Naziv: ${it.title}"
            binding.tvPrice.text = "Cijena: €${it.price}"
            binding.tvInfo.text = "Opis: ${it.info}"
        }

        binding.btnBuy.visibility = if (cartList.isEmpty()) View.VISIBLE else View.GONE

        // Klik na gumb "Dodaj u kosaricu" ili "Ukloni iz kosarice"
        binding.btnAddToCart.setOnClickListener {
            if (from == "cart") {
                binding.btnAddToCart.text = "Ukloni iz košarice"
                cartList.remove(productModel)
                Toast.makeText(this, "Uklonjeno iz košarice.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                cartList.add(productModel!!)
                Toast.makeText(this, "Proizvod dodan u košaricu.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Klik na gub "Kupi odmah"
        binding.btnBuy.setOnClickListener {
            showCheckoutDialog()
        }

        // Cekanje rezultata placanja i rukovanje sukladno rezultatu
        getOrderTokenViewModel.paymentResult.observe(this) { paymentResponse ->
            encryptedOrderToken = ""
            paymentResponse?.let {
                Log.d("Placanje", "Payment ID: ${it.id}, Token: ${it.token}")
                encryptedOrderToken = DataEncryption.encryptData(it.token)
                if (it.token != null) {
                    val decryptedEmail = DataEncryption.decryptData(encryptedEmail)
                    val decryptedAddress = DataEncryption.decryptData(encryptedDeliveryAddress)
                    // Pokretanje Revolut Payment Intenta
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
                    // Obavjestavamo korisnika ako je placanje uspjelo ali order token nije pronadjen
                    Toast.makeText(
                        this,
                        "Plaćanje uspješno, ali order token nije pronađen.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } ?: run {
                // Obavjestavamo korisnika po neuspjelom placanju
                Toast.makeText(this, "Placanje neuspješno.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Rukovanje rezultatom aktivnosti placanja
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = RevolutPaymentApi.mapIntentToResult(resultCode, data)

        if (result != null) {
            status = ""
            when (result) {
                is com.revolut.cardpayments.api.Result.Authorised -> {
                    // Placanje uspjesno
                    status = "Payment Successful!"
                    Toast.makeText(this, "Plaćanje uspješno!", Toast.LENGTH_LONG).show()
                    cartList.clear()
                    finish()
                    Log.d("Placanje", "Placanje uspjesno!")
                }

                is com.revolut.cardpayments.api.Result.Failed -> {
                    // Placanje neuspjesno zbog specificnog razloga
                    status = "Payment Failed: ${result.failureReason.name}"
                    Toast.makeText(
                        this, "Plaćanje neuspješno: ${result.failureReason.name}", Toast.LENGTH_LONG
                    ).show()
                    Log.e("Placanje", "Placanje neuspjesno: ${result.failureReason.name}")
                }

                is com.revolut.cardpayments.api.Result.Declined -> {
                    // Placanje odbijeno od strane korisnika
                    status = "Payment Cancelled by User"
                    Toast.makeText(this, "Plaćanje prekinuo korisnik", Toast.LENGTH_LONG).show()
                    Log.d("Placanje", "Placanje prekinuo korisnik.")
                }

                else -> {}
            }

            if (status.isNotEmpty()) {
                val transactionModel = TransactionModel(
                    name = encryptedName,
                    email = encryptedEmail,
                    phone = encryptedPhone,
                    deliveryAddress = encryptedDeliveryAddress,
                    totalAmount = productModel?.price.toString(),
                    orderToken = encryptedOrderToken,
                    status = status
                )
                transactionViewModel.insertTransaction(transactionModel)
            }

        } else {
            // Rukovanje nepoznatim greskama
            Toast.makeText(this, "Dogodila se nepoznata pogreška.", Toast.LENGTH_LONG).show()
            Log.e("Placanje", "Nepoznata pogreska ili rezultat null.")
        }
    }

    private fun showCheckoutDialog() {
        val dialog = Dialog(this)
        val bindingDialog = ItemCheckoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)

        // Postavljanje dijalog prozora sa sirinom na match_parent i transparentnim fontom
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Postavljanje 10 dp margine
        val params = bindingDialog.root.layoutParams as ViewGroup.MarginLayoutParams
        val marginInPixels = (10 * resources.displayMetrics.density).toInt() // Pretvorba dp u pixele
        params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels)
        bindingDialog.root.layoutParams = params

        // Slusac klika na gumb Potvrdi
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
                productModel?.price?.let { it1 -> getOrderTokenViewModel.initiatePayment(it1) }
            }
        }

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun validateInput(
        name: String,
        email: String,
        phone: String,
        deliveryAddress: String
    ): Boolean {
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
            Toast.makeText(this, "Molimo unesite adresu", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
