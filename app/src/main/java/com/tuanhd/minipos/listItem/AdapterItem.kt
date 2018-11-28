package com.tuanhd.minipos.listItem

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item

class AdapterItem internal constructor(context: Context): RecyclerView.Adapter<ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<Item>()

    internal fun addAll(data: List<Item>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItemViewHolder {
        val view = inflater.inflate(R.layout.layout_item, viewGroup, false)
        return ItemViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val item = items[position]
        viewHolder.bindItem(item)
    }

}