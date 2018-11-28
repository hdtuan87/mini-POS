package com.tuanhd.minipos.listItem

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val thumb = itemView.findViewById<ImageView>(R.id.imvThumb)
    private val name = itemView.findViewById<TextView>(R.id.txvName)
    private val price = itemView.findViewById<TextView>(R.id.txvPrice)

    fun bindItem(item: Item) {
        name.text = item.name
        price.text = item.price.toString()
    }
}