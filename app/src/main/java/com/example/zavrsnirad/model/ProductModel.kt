package com.example.zavrsnirad.model

import java.io.Serializable

data class ProductModel(
    val id: Int,
    val title: String,
    val price: Int,
    val info: String,
    val image: Int
):Serializable
