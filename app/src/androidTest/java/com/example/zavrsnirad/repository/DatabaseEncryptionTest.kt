package com.example.zavrsnirad.repository

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.utils.DataEncryption
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseEncryptionTest {

    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        dbHelper = DatabaseHelper(ApplicationProvider.getApplicationContext())
        dbHelper.clearAllHistory()
    }

    @After
    fun tearDown() {
        dbHelper.clearAllHistory()
        dbHelper.closeDb()
    }

    @Test
    fun test_enkripcije_i_dekripcije() {
        val originalText = "Ovo je tajni podatak"
        val encryptedText = DataEncryption.encryptData(originalText)
        val decryptedText = DataEncryption.decryptData(encryptedText)
        assertNotEquals(originalText, encryptedText)
        assertEquals(originalText, decryptedText)
    }

    @Test
    fun unos_transakcije_sprema_enkriptirane_podatke() {
        val plainName = "Ivan Horvat"
        val plainEmail = "ivan.horvat@example.com"
        val encryptedName = DataEncryption.encryptData(plainName)
        val encryptedEmail = DataEncryption.encryptData(plainEmail)

        val transaction = TransactionModel(
            name = encryptedName,
            email = encryptedEmail,
            phone = DataEncryption.encryptData("0987654321"),
            deliveryAddress = DataEncryption.encryptData("Zagrebacka 5"),
            totalAmount = "100",
            orderToken = DataEncryption.encryptData("token123"),
            status = "Payment Successful!"
        )

        dbHelper.insertTransaction(transaction)

        val storedTransactions = dbHelper.getAllTransactionsRaw()
        val storedTransaction = storedTransactions[0]

        assertNotNull(storedTransaction)
        assertEquals(1, storedTransactions.size)
        assertEquals(encryptedName, storedTransaction.name)
        assertNotEquals(plainName, storedTransaction.name)

        val decryptedName = DataEncryption.decryptData(storedTransaction.name)
        val decryptedEmail = DataEncryption.decryptData(storedTransaction.email)
        assertEquals(plainName, decryptedName)
        assertEquals(plainEmail, decryptedEmail)
    }
}