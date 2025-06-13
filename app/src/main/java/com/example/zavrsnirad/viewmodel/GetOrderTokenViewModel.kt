package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.model.PaymentRequest
import com.example.zavrsnirad.model.PaymentResponse
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetOrderTokenViewModel(databaseHelper: DatabaseHelper) : ViewModel() {

    // Instanca PaymentRepository za placanje
    private val paymentRepository = PaymentRepository(databaseHelper)

    // LiveData za pracenje rezultata placanja
    private val _paymentResult = MutableLiveData<PaymentResponse?>()
    val paymentResult: LiveData<PaymentResponse?> get() = _paymentResult

    // Funkcija za inicijalizaciju placanja
    fun initiatePayment(price: Int) {
        val amountInCents = price * 100 // Konverzija cijene u cente
        val request = PaymentRequest(amount = amountInCents, currency = "EUR") // Kreiranje paymentrequest objekta
        paymentRepository.initiatePayment(request) { response ->
            _paymentResult.postValue(response) // Update LiveData sa odgovorom
        }
    }

    fun insertTransaction(transaction: TransactionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.insertTransaction(transaction)
        }
    }
}


