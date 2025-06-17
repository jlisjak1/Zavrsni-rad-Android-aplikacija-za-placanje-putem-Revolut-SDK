package com.example.zavrsnirad.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RevolutApiClient {
    private const val BASE_URL = "https://sandbox-merchant.revolut.com/api/"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer sk_DvS3VqlJyMV22o2TMy_OWfXtDUqzPWR-A1LClOhW7p2OdW7bYDKQtWZwG5eCzBIr")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Revolut-Api-Version", "2024-09-01")
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: RevolutApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RevolutApiService::class.java)
    }
}