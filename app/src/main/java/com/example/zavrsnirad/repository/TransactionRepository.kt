package com.example.zavrsnirad.repository

import com.example.zavrsnirad.model.TransactionModel

class TransactionRepository(private val databaseHelper: DatabaseHelper) {

    // Funkcija za umetanje transakcije u bazu
    fun insertTransaction(history: TransactionModel) {
        databaseHelper.insertTransaction(history)
    }



}
