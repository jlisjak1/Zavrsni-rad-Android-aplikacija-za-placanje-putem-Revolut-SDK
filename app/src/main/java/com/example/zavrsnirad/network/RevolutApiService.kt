package com.example.zavrsnirad.network

import com.example.zavrsnirad.model.PaymentRequest
import com.example.zavrsnirad.model.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RevolutApiService {
    // API endpoint za kreaciju placanja
    @POST("orders")
    fun createPayment(@Body request: PaymentRequest): Call<PaymentResponse>
}
