package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zavrsnirad.model.PaymentRequest
import com.example.zavrsnirad.model.PaymentResponse
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.repository.PaymentRepository

class GetOrderTokenViewModel(databaseHelper: DatabaseHelper) : ViewModel() {

    private val paymentRepository = PaymentRepository(databaseHelper)

    private val _paymentResult = MutableLiveData<PaymentResponse?>()
    val paymentResult: LiveData<PaymentResponse?> get() = _paymentResult

    fun initiatePayment(price: Int) {
        val amountInCents = price * 100
        val request = PaymentRequest(amount = amountInCents, currency = "EUR")
        paymentRepository.initiatePayment(request) { response ->
            _paymentResult.postValue(response)
        }
    }

}