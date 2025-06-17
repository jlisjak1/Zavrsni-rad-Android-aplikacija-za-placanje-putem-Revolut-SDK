package com.example.zavrsnirad.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.utils.DataEncryption
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseHelperTest {

    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        dbHelper = DatabaseHelper(context)
        dbHelper.clearAllHistory()
    }

    @After
    fun tearDown() {
        dbHelper.closeDb()
    }

    @Test
    fun umetanje_i_dohvat_sirove_transakcije_provjerava_enkripciju() {
        val originalName = "Ivan Horvat"
        val originalEmail = "ivan.horvat@example.com"
        val transaction = TransactionModel(
            name = DataEncryption.encryptData(originalName),
            email = DataEncryption.encryptData(originalEmail),
            phone = DataEncryption.encryptData("0987654321"),
            deliveryAddress = DataEncryption.encryptData("Zagrebačka 5"),
            totalAmount = "100",
            orderToken = DataEncryption.encryptData("token123"),
            status = "Uspješno"
        )

        dbHelper.insertTransaction(transaction)
        val transactionsRaw = dbHelper.getAllTransactionsRaw()

        assertEquals(1, transactionsRaw.size)
        val retrievedRaw = transactionsRaw[0]

        assertNotEquals(originalName, retrievedRaw.name)
        assertNotEquals(originalEmail, retrievedRaw.email)

        assertEquals(originalName, DataEncryption.decryptData(retrievedRaw.name))
        assertEquals(originalEmail, DataEncryption.decryptData(retrievedRaw.email))
    }
}