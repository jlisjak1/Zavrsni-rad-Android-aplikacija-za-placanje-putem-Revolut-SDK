package com.example.zavrsnirad.repository

import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.model.PaymentRequest
import com.example.zavrsnirad.model.PaymentResponse
import com.example.zavrsnirad.network.RevolutApiClient
import com.example.zavrsnirad.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentRepository(private val databaseHelper: DatabaseHelper) {

    // Funkcija za inicirranje placanja koriscenjem Revolut API-ja
    fun initiatePayment(request: PaymentRequest, callback: (PaymentResponse?) -> Unit) {
        // "Upali semafor" - reci Espressu da čeka
        EspressoIdlingResource.increment()
        RevolutApiClient.instance.createPayment(request).enqueue(object : Callback<PaymentResponse> {
            // Rukovanje odgovorom uspjeha od API-a
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Dodavanje response bodya callbacku
                } else {
                    callback(null) // Dodavanje null odgovora za neuspjeh
                }
                // "Ugasi semafor" - reci Espressu da može nastaviti
                EspressoIdlingResource.decrement()
            }

            // Rukovanje neuspjehom tokom API poziva
            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                callback(null) // Dodavanje nulla u slucaju neuspjeha
                // "Ugasi semafor" i u slučaju greške
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun insertTransaction(transaction: TransactionModel) {
        databaseHelper.insertTransaction(transaction)
    }



}
