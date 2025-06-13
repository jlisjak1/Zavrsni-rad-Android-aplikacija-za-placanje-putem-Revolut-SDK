package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    // Funkcija za umetanje redka u bazu podataka
    fun insertTransaction(transaction: TransactionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insertTransaction(transaction)
        }
    }
}
