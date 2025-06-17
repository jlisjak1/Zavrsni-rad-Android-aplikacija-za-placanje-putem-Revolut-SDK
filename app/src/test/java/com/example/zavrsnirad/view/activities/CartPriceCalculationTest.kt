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
    fun izracun_cijene_vraca_ispravan_zbroj_za_vise_artikala() {
        val product1 = ProductModel(1, "Laptop", 10000, "Opis A", 0)
        val product2 = ProductModel(2, "Miš", 200, "Opis B", 0)
        val product3 = ProductModel(3, "Tipkovnica", 500, "Opis C", 0)
        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)
        MainActivity.cartList.add(product3)
        val expectedTotalPrice = 10000 + 200 + 500
        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }
        assertEquals(expectedTotalPrice, actualTotalPrice)
    }

    @Test
    fun izracun_cijene_vraca_nulu_za_praznu_kosaricu() {
        val expectedTotalPrice = 0
        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }
        assertEquals(expectedTotalPrice, actualTotalPrice)
    }

    @Test
    fun izracun_cijene_vraca_ispravan_zbroj_za_jedan_artikl() {
        val product1 = ProductModel(1, "Laptop", 10000, "Opis A", 0)
        MainActivity.cartList.add(product1)
        val expectedTotalPrice = 10000
        val actualTotalPrice = MainActivity.cartList.sumOf { it.price }
        assertEquals(expectedTotalPrice, actualTotalPrice)
    }
}