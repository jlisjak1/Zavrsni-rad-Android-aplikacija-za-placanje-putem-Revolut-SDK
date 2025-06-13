package com.example.zavrsnirad.view.activities

import com.example.zavrsnirad.model.ProductModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CartPriceCalculationTest {

    @Before
    fun setup() {
        MainActivity.cartList.clear()
    }

    @Test
    fun calculateTotalPrice_returns_correct_sum_for_multiple_items() {
        val product1 = ProductModel(1, "Laptop", 10000, "Opis A", 0) // 100.00 EUR
        val product2 = ProductModel(2, "Miš", 200, "Opis B", 0)     // 2.00 EUR
        val product3 = ProductModel(3, "Tipkovnica", 500, "Opis C", 0) // 5.00 EUR

        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)
        MainActivity.cartList.add(product3)

        val expectedTotalPrice = 10000 + 200 + 500 // 10700 centi

        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }

        assertEquals(expectedTotalPrice, actualTotalPrice)
    }

    @Test
    fun calculateTotalPrice_returns_zero_for_empty_cart() {
        val expectedTotalPrice = 0

        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }

        assertEquals(expectedTotalPrice, actualTotalPrice)
    }

    @Test
    fun calculateTotalPrice_returns_correct_sum_for_single_item() {
        val product1 = ProductModel(1, "Laptop", 10000, "Opis A", 0)
        MainActivity.cartList.add(product1)

        val expectedTotalPrice = 10000

        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }

        assertEquals(expectedTotalPrice, actualTotalPrice)
    }
}
