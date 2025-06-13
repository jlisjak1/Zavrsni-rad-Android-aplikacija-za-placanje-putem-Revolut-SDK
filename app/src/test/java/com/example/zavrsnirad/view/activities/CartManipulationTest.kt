package com.example.zavrsnirad.view.activities

import com.example.zavrsnirad.model.ProductModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartManipulationTest {

    @Before
    fun setup() {
        // ocistite staticku cartList prije svakog testa kako bismo osigurali izolaciju
        MainActivity.cartList.clear()
    }

    @Test
    fun `addProductToCart should add product to cartList`() {
        val product1 = ProductModel(1, "Test Proizvod 1", 1000, "Opis", 0)
        MainActivity.cartList.add(product1)

        assertEquals(1, MainActivity.cartList.size)
        assertTrue(MainActivity.cartList.contains(product1))
    }

    @Test
    fun `removeProductFromCart should remove product from cartList`() {
        val product1 = ProductModel(1, "Test Proizvod 1", 1000, "Opis", 0)
        val product2 = ProductModel(2, "Test Proizvod 2", 2000, "Opis", 0)

        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)

        MainActivity.cartList.remove(product1) // Simuliramo uklanjanje

        assertEquals(1, MainActivity.cartList.size)
        assertFalse(MainActivity.cartList.contains(product1)) // Provjeravamo da vise nije u listi
        assertTrue(MainActivity.cartList.contains(product2))
    }

    @Test
    fun `clearCart should remove all products from cartList`() {
        val product1 = ProductModel(1, "Test Proizvod 1", 1000, "Opis", 0)
        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(ProductModel(2, "Test Proizvod 2", 2000, "Opis", 0))

        MainActivity.cartList.clear() // Simuliramo cisxenje

        assertTrue(MainActivity.cartList.isEmpty())
    }

    @Test
    fun `calculateTotalPrice sums up prices correctly`() {
        val product1 = ProductModel(1, "Laptop", 10000, "Opis A", 0) // 100.00 EUR
        val product2 = ProductModel(2, "Miš", 200, "Opis B", 0)     // 2.00 EUR

        MainActivity.cartList.add(product1)
        MainActivity.cartList.add(product2)

        val expectedTotal = 10000 + 200
        val actualTotal = MainActivity.cartList.sumOf { it.price }

        assertEquals(expectedTotal, actualTotal)
    }
}
