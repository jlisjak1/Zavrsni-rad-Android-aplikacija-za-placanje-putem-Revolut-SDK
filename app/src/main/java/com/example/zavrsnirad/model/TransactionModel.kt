package com.example.zavrsnirad.model

import java.io.Serializable

data class TransactionModel(
    val id: Int? = null,
    val name: String,
    val email: String,
    val phone: String,
    val deliveryAddress: String,
    val totalAmount: String,
    val orderToken: String,
    val status: String
):Serializable
