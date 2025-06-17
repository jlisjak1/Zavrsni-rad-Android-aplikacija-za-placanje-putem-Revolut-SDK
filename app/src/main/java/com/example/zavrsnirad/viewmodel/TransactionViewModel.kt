package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    // LiveData koji ce sadrzavati listu transakcija za prikaz
    private val _transactions = MutableLiveData<List<TransactionModel>>()
    val transactions: LiveData<List<TransactionModel>> get() = _transactions

    // Funkcija koja pokrece ucitavanje transakcija
    fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val transactionList = transactionRepository.getAllDecryptedTransactions()
            _transactions.postValue(transactionList)
        }
    }

    // Funkcija za umetanje (ostaje ista)
    fun insertTransaction(transaction: TransactionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insertTransaction(transaction)
        }
    }
}