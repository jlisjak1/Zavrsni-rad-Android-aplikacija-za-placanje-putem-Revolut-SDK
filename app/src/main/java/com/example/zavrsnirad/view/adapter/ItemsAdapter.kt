package com.example.zavrsnirad.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zavrsnirad.utils.OnClick
import com.example.zavrsnirad.databinding.ItemShopBinding
import com.example.zavrsnirad.model.ProductModel

class ItemsAdapter(
    var items: List<ProductModel>,
    var onClick: OnClick? = null
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private lateinit var context: android.content.Context

    fun updateList(items: List<ProductModel>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemShopBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvName.text = item.title
        holder.binding.tvPrice.text = "€" + item.price
        holder.binding.ivImage.setImageResource(item.image)

        holder.itemView.setOnClickListener {
            onClick?.clicked(position)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root)

}