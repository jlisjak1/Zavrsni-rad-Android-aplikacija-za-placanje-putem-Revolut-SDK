package com.example.zavrsnirad.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.zavrsnirad.model.TransactionModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTransactionTable = """
            CREATE TABLE $TABLE_TRANSACTION (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $KEY_NAME TEXT NOT NULL, 
                $KEY_EMAIL TEXT NOT NULL, 
                $KEY_PHONE TEXT NOT NULL, 
                $KEY_DELIVERY_ADDRESS TEXT NOT NULL, 
                $KEY_TOTAL_AMOUNT TEXT NOT NULL, 
                $KEY_ORDER_TOKEN TEXT NOT NULL, 
                $KEY_STATUS TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTransactionTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTION")
        onCreate(db)
    }

    // Umetanje transakcije
    fun insertTransaction(transaction: TransactionModel) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(KEY_NAME, transaction.name)
                put(KEY_EMAIL, transaction.email)
                put(KEY_PHONE, transaction.phone)
                put(KEY_DELIVERY_ADDRESS, transaction.deliveryAddress)
                put(KEY_TOTAL_AMOUNT, transaction.totalAmount)
                put(KEY_ORDER_TOKEN, transaction.orderToken)
                put(KEY_STATUS, transaction.status)
            }
            db.insert(TABLE_TRANSACTION, null, values)
        }
    }

    // Dohvatu sve transakcije
    fun getAllTransactions(): List<TransactionModel> {
        val transactionList = mutableListOf<TransactionModel>()
        readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM $TABLE_TRANSACTION", null).use { cursor ->
                while (cursor.moveToNext()) {
                    transactionList.add(
                        TransactionModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_DELIVERY_ADDRESS)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TOTAL_AMOUNT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_ORDER_TOKEN)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS))
                        )
                    )
                }
            }
        }
        return transactionList
    }

    // Dodana metoda za čiscenje baze za potrebe testiranja
    fun clearAllHistory() {
        writableDatabase.use { db ->
            db.delete(TABLE_TRANSACTION, null, null)
        }
    }

    // Dodana metoda za zatvaranje baze
    fun closeDb() {
        close()
    }

    // Dodana metoda za dohvat SVIH transakcija BEZ dekripcije
    fun getAllTransactionsRaw(): List<TransactionModel> {
        val transactionList = mutableListOf<TransactionModel>()
        readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM $TABLE_TRANSACTION", null).use { cursor ->
                while (cursor.moveToNext()) {
                    transactionList.add(
                        TransactionModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_DELIVERY_ADDRESS)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TOTAL_AMOUNT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_ORDER_TOKEN)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_STATUS))
                        )
                    )
                }
            }
        }
        return transactionList
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "transaction_database.db"

        private const val TABLE_TRANSACTION = "transaction_table"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
        private const val KEY_DELIVERY_ADDRESS = "delivery_address"
        private const val KEY_TOTAL_AMOUNT = "total_amount"
        private const val KEY_ORDER_TOKEN = "order_token"
        private const val KEY_STATUS = "status"
    }
}
