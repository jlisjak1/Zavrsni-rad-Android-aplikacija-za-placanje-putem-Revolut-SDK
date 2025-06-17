package com.example.zavrsnirad.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zavrsnirad.databinding.ActivityTransactionHistoryBinding
import com.example.zavrsnirad.repository.DatabaseHelper
import com.example.zavrsnirad.repository.TransactionRepository
import com.example.zavrsnirad.view.adapter.TransactionAdapter
import com.example.zavrsnirad.viewmodel.TransactionViewModel
import com.example.zavrsnirad.viewmodel.TransactionViewModelFactory

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ISPRAVAK: Uklonili smo setSupportActionBar i dodali OnClickListener
        binding.ivBack.setOnClickListener { finish() }

        setupRecyclerView()

        val databaseHelper = DatabaseHelper(this)
        val transactionRepository = TransactionRepository(databaseHelper)
        val factory = TransactionViewModelFactory(transactionRepository)
        viewModel = ViewModelProvider(this, factory).get(TransactionViewModel::class.java)

        viewModel.transactions.observe(this) { transactions ->
            if (transactions.isEmpty()) {
                binding.rvTransactions.visibility = View.GONE
                binding.tvNoTransactions.visibility = View.VISIBLE
            } else {
                binding.rvTransactions.visibility = View.VISIBLE
                binding.tvNoTransactions.visibility = View.GONE
                transactionAdapter.updateData(transactions)
            }
        }

        viewModel.loadTransactions()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        binding.rvTransactions.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(this@TransactionHistoryActivity)
        }
    }
}