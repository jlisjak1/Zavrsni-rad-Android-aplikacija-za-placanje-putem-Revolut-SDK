package com.example.zavrsnirad.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RevolutApiClient {
    private const val DEFAULT_BASE_URL = "https://sandbox-merchant.revolut.com/api/"
    private var currentBaseUrl = DEFAULT_BASE_URL

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

    // Uklanjamo lazy inicijalizaciju da bismo mogli dinamički mijenjati URL za testiranje
    val instance: RevolutApiService
        get() = buildRetrofitService(currentBaseUrl)

    // Metoda za postavljanje baznog URL-a za testiranje
    fun setBaseUrlForTesting(baseUrl: String) {
        currentBaseUrl = baseUrl
    }

    // Metoda za resetiranje na defaultni URL nakon testiranja
    fun resetBaseUrl() {
        currentBaseUrl = DEFAULT_BASE_URL
    }

    private fun buildRetrofitService(baseUrl: String): RevolutApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RevolutApiService::class.java)
    }
}