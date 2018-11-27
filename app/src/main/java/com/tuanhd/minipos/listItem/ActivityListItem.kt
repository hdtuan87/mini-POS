package com.tuanhd.minipos.listItem

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import kotlinx.android.synthetic.main.activity_list_item.*

class ActivityListItem : AppCompatActivity() {
    companion object {
        const val addItemRequestCode = 1
        const val extraItem = "extra_item"
    }

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        val adapter = AdapterItem()
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.allItem.observe(this, Observer {data ->
            data?.let { adapter.addAll(it) }
        })

        listItem.adapter = AdapterItem()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) return

        if (requestCode == addItemRequestCode){
            data?.let {
                val item = it.getSerializableExtra(extraItem) as Item
                itemViewModel.insert(item)
            }
        }
    }
}