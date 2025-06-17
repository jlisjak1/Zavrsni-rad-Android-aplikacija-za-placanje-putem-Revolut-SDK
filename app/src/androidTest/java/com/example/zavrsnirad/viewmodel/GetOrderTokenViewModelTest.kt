package com.example.zavrsnirad.viewmodel

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetOrderTokenViewModelTest {

    private lateinit var viewModel: TransactionViewModel
    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        dbHelper = DatabaseHelper(context)
        dbHelper.clearAllHistory()

        val transactionRepository = TransactionRepository(dbHelper)

        viewModel = TransactionViewModel(transactionRepository)
    }

    @After
    fun tearDown() {
        dbHelper.clearAllHistory()
        dbHelper.closeDb()
    }

    @Test
    fun umetanje_transakcije_u_viewmodel_ispravno_sprema_u_bazu() = runBlocking {
        val testTransaction = TransactionModel(
            name = "Pero",
            email = "pero@test.com",
            phone = "123",
            deliveryAddress = "Adresa 1",
            totalAmount = "100",
            orderToken = "token123",
            status = "Uspjesno"
        )

        viewModel.insertTransaction(testTransaction)

        Thread.sleep(500)

        val transactionsInDb = dbHelper.getAllTransactions()

        assertEquals(1, transactionsInDb.size)
        assertTrue(transactionsInDb.any { it.name == "Pero" && it.orderToken == "token123" })
    }
}