package com.example.zavrsnirad.repository

import com.example.zavrsnirad.model.ProductModel

interface IProductRepository {
    fun getProductList(): List<ProductModel>
}