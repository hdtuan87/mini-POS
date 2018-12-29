package com.tuanhd.minipos.listItem

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import io.reactivex.subjects.PublishSubject

class AdapterItem internal constructor(context: Context): RecyclerView.Adapter<ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    var items = ArrayList<Item>()

    private var publishSubject = PublishSubject.create<Item>()

    internal fun clear(){
        items.clear()
    }

    internal fun addAll(data: List<Item>) {
        items.addAll(data)
    }

    internal fun add(item: Item){
        items.add(item)
    }

    internal fun setListener(publishSubject: PublishSubject<Item>){
        this.publishSubject = publishSubject
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItemViewHolder {
        val view = inflater.inflate(R.layout.layout_item, viewGroup, false)

        return ItemViewHolder(view, publishSubject)
    }


    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val item = items[position]
        viewHolder.bindItem(item)
    }

}