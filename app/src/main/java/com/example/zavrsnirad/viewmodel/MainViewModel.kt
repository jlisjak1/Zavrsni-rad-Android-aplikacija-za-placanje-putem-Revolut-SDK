package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.repository.IProductRepository
import com.example.zavrsnirad.repository.ProductRepository

// Stara verzija
// class MainViewModel : ViewModel() {
//     private val repository = ProductRepository()

class MainViewModel(private val repository: IProductRepository = ProductRepository()) : ViewModel() {

    // Instanca ProductRepository za dohvacanje podataka o kupovini
    //private val repository = ProductRepository()

    // LiveData za pracenje podataka iz liste proizvoda
    private val _productList = MutableLiveData<List<ProductModel>>()
    val productList: LiveData<List<ProductModel>> get() = _productList

    init {
        // Ucitavanje liste proizvoda prilikom inicijalizacije ViewModel-a
        loadProductList()
    }

    // Funkcija za ucitavanje liste proizvoda u LiveData
    private fun loadProductList() {
        _productList.value = repository.getProductList()
    }
}
