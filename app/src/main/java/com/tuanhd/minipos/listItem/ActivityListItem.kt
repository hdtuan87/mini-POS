package com.tuanhd.minipos.listItem

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.tuanhd.minipos.ActivityAddItem
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import kotlinx.android.synthetic.main.activity_list_item.*

class ActivityListItem : AppCompatActivity() {
    companion object {
        const val addItemRequestCode = 1
    }

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val adapter = AdapterItem(this)
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.allItems.observe(this, Observer {data ->
            data?.let { adapter.addAll(it) }
        })

        listItem.layoutManager = LinearLayoutManager(this)
        listItem.adapter = adapter


        btnAddItem.setOnClickListener { startActivityAddItem() }
    }

    private fun startActivityAddItem() {
        val intent = Intent(this, ActivityAddItem::class.java)
        startActivityForResult(intent, addItemRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) return

        if (requestCode == addItemRequestCode){
            data?.let {
                val item = it.getSerializableExtra(ActivityAddItem.extraItem) as Item
                itemViewModel.insert(item)
            }
        }
    }
}