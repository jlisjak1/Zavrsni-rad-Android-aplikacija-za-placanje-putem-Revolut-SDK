package com.example.zavrsnirad.repository

import com.example.zavrsnirad.model.TransactionModel
import com.example.zavrsnirad.utils.DataEncryption

class TransactionRepository(private val databaseHelper: DatabaseHelper) {

    fun insertTransaction(transaction: TransactionModel) {
        databaseHelper.insertTransaction(transaction)
    }

    fun getAllDecryptedTransactions(): List<TransactionModel> {
        val encryptedTransactions = databaseHelper.getAllTransactionsRaw()
        return encryptedTransactions.map { transaction ->
            TransactionModel(
                id = transaction.id,
                name = DataEncryption.decryptData(transaction.name),
                email = DataEncryption.decryptData(transaction.email),
                phone = DataEncryption.decryptData(transaction.phone),
                deliveryAddress = DataEncryption.decryptData(transaction.deliveryAddress),
                totalAmount = transaction.totalAmount, // Cijena nije enkriptirana
                orderToken = DataEncryption.decryptData(transaction.orderToken),
                status = transaction.status // Status nije enkriptiran
            )
        }
    }
}