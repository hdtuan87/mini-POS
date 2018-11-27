package com.tuanhd.minipos.listItem

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item

class AdapterItem : RecyclerView.Adapter<ItemViewHolder>() {
    private var items: ArrayList<Item> = ArrayList()

    fun addAll(data: ArrayList<Item>) {
        items = data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val item = items[position]
        viewHolder.bindItem(item)
    }

}