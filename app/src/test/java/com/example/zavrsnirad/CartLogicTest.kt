package com.example.zavrsnirad

import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.view.activities.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartLogicTest {

    @Before
    fun setUp() {
        MainActivity.cartList.clear()
    }

    @Test
    fun test_dodavanja_artikla_u_kosaricu() {
        val product = ProductModel(1, "Test Proizvod", 100, "Opis", 123)
        MainActivity.cartList.add(product)
        assertEquals(1, MainActivity.cartList.size)
        assertTrue(MainActivity.cartList.contains(product))
    }

    @Test
    fun test_uklanjanja_artikla_iz_kosarice() {
        val product = ProductModel(1, "Test Proizvod", 100, "Opis", 123)
        MainActivity.cartList.add(product)
        MainActivity.cartList.remove(product)
        assertTrue(MainActivity.cartList.isEmpty())
    }

    @Test
    fun test_izracuna_ukupne_cijene() {
        val product1 = ProductModel(1, "Proizvod 1", 100, "Opis", 1)
        val product2 = ProductModel(2, "Proizvod 2", 250, "Opis", 2)
        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)
        val totalAmount = MainActivity.cartList.sumOf { it.price }
        assertEquals(350, totalAmount)
    }
}