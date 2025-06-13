package com.example.zavrsnirad.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zavrsnirad.databinding.ActivityMainBinding
import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.view.adapter.ItemsAdapter
import com.example.zavrsnirad.utils.OnClick
import com.example.zavrsnirad.viewmodel.MainViewModel
import com.revolut.cardpayments.api.Environment
import com.revolut.cardpayments.api.RevolutPaymentApi

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var itemsAdapter: ItemsAdapter

    // Staticna lista  za pohranu proizvoda u kosarici, dostupna kroz aktivnosti
    companion object {
        var cartList: ArrayList<ProductModel> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflacija layouta koristenjem ViewBinding-a
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicijalizacija RevoluT API-a u sandbox okruzenju sa javnim kljucem
        RevolutPaymentApi.init(
            Environment.SANDBOX, //Za prebacivanje na produkcijsku verziju promijeniti na Environment.PRODUCTION
            "pk_GpOBuYQmZc9utnCRivJ5Udq598jqNaW7v1Vh5mrWXDG4u3PU"
        )



        // Inicijalizacija ViewModela
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Postavljanje RecyclerView-a i adaptea
        setupRecyclerView()
        // Trazenje promjena u ViwewModelu
        observeViewModel()

        // Slusac na klik za ikonicu kosarice i navigaiciju na CartActivity
        binding.ivCart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    // Konfigurancija RecyclerView-a sa adapterom i layout managerom
    private fun setupRecyclerView() {
        // Inicijalizacija adaptera sa praznom listom i slucajem klik
        itemsAdapter = ItemsAdapter(emptyList(), object : OnClick {
            override fun clicked(pos: Int) {
                // Dohvacanje odabranog proizvoda od ViewModelove liste
                val shopModel = viewModel.productList.value?.get(pos)
                shopModel?.let {
                    // Pokretanje aktivnosti sa detaljima odabranog proizvoda i prosledjenim podacima
                    val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
                    intent.putExtra("from", "main")
                    intent.putExtra("data", it)
                    startActivity(intent)
                }
            }
        })
        // Postavljanje layout managera i dodavanje adaptera RecyclerView-u
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = itemsAdapter
    }

    // Pracenje liste proizvoda u ViewModel-u i azuriranje RecyclerView-a
    private fun observeViewModel() {
        viewModel.productList.observe(this) { shoppingList ->
            // Update adaptera sa novom listom proizvoda
            itemsAdapter.updateList(shoppingList)
        }
    }

    override fun onResume() {
        super.onResume()

        val databaseHelper = DatabaseHelper(this)
        Log.d("Provjera povjesti", "onResume: "+databaseHelper.getAllTransactions())

    }

}

