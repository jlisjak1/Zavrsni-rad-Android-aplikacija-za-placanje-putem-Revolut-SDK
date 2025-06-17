package com.example.zavrsnirad.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zavrsnirad.databinding.ItemTransactionBinding
import com.example.zavrsnirad.model.TransactionModel

class TransactionAdapter(private var transactions: List<TransactionModel>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    fun updateData(newTransactions: List<TransactionModel>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: TransactionModel) {
            binding.tvTransactionStatus.text = transaction.status
            binding.tvTransactionAmount.text = "Iznos: €${transaction.totalAmount}"
            binding.tvTransactionCustomer.text = "Kupac: ${transaction.name}"
            binding.tvTransactionToken.text = "Token: ${transaction.orderToken}"
        }
    }
}