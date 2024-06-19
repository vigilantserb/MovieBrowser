package com.stameni.com.moviebrowser.common.baseClasses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.stameni.com.moviebrowser.data.models.Genre

// BaseViewHolder.kt
class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

// BaseAdapter.kt
abstract class BaseAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB
) : RecyclerView.Adapter<BaseViewHolder<VB>>() {

    internal val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = items[position]
        bind(holder.binding, item, position)
    }

    override fun getItemCount(): Int = items.size

    abstract fun add(item: T)

    abstract fun addAll(items: List<T>)

    abstract fun bind(binding: VB, item: T, position: Int)

    fun submitList(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
