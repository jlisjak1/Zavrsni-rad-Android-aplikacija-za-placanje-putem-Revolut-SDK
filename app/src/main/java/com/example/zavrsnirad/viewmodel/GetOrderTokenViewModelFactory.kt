package com.example.zavrsnirad.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zavrsnirad.repository.DatabaseHelper

class GetOrderTokenViewModelFactory(private val databaseHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetOrderTokenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GetOrderTokenViewModel(databaseHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}