package com.example.zavrsnirad

import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.view.activities.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartLogicTest {

    // Priprema okruzenja prije svakog testa
    @Before
    fun setUp() {
        MainActivity.cartList.clear()
    }

    @Test
    fun `test adding item to cart`() {
        // Priprema
        val product = ProductModel(1, "Test Proizvod", 100, "Opis", 123)

        // Akcija
        MainActivity.cartList.add(product)

        // Provjera
        assertEquals(1, MainActivity.cartList.size)
        assertTrue(MainActivity.cartList.contains(product))
    }

    @Test
    fun `test removing item from cart`() {
        // Priprema
        val product = ProductModel(1, "Test Proizvod", 100, "Opis", 123)
        MainActivity.cartList.add(product)

        // Akcija
        MainActivity.cartList.remove(product)

        // Provjera
        assertTrue(MainActivity.cartList.isEmpty())
    }

    @Test
    fun `test calculating total price`() {
        // Priprema
        val product1 = ProductModel(1, "Proizvod 1", 100, "Opis", 1)
        val product2 = ProductModel(2, "Proizvod 2", 250, "Opis", 2)
        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)

        // Akcija
        val totalAmount = MainActivity.cartList.sumOf { it.price }

        // Provjera
        assertEquals(350, totalAmount)
    }
}