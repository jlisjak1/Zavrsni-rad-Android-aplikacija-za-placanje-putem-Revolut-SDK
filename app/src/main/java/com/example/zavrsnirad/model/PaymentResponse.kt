package com.example.zavrsnirad.model

data class PaymentResponse(
    val id: String,
    val token: String,
    val type: String,
    val state: String,
    val created_at: String,
    val updated_at: String,
    val amount: Int,
    val currency: String,
    val outstanding_amount: Int,
    val capture_mode: String,
    val checkout_url: String,
    val enforce_challenge: String
)
