package com.tuanhd.minipos.listItem

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.tuanhd.minipos.addItem.ActivityAddItem
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_list_item.*

class ActivityListItem : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var observer: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val adapter = AdapterItem(this)
        listItem.layoutManager = LinearLayoutManager(this)
        listItem.adapter = adapter

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.allItems.observe(this, Observer { data ->
            data?.let {
                updateAdapter(adapter, it)
            }
        })
        val publishSubject = PublishSubject.create<Item>()
        adapter.setListener(publishSubject)

        observer = publishSubject.subscribe {
            startEditActivity(it)
        }

        btnAddItem.setOnClickListener { startActivityAddItem() }
    }

    private fun startEditActivity(item: Item) {
        val intent = Intent(this, ActivityAddItem::class.java)
        intent.putExtra(ActivityAddItem.EXTRA_ITEM, item)
        startActivity(intent)
    }

    private fun updateAdapter(adapter: AdapterItem, it: List<Item>) {
        adapter.clear()
        adapter.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private fun startActivityAddItem() {
        val intent = Intent(this, ActivityAddItem::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        if (!observer.isDisposed){
            observer.dispose()
        }
        super.onDestroy()
    }

}