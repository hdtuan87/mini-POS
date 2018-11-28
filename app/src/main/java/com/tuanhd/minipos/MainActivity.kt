package com.tuanhd.minipos

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.listItem.ActivityListItem
import com.tuanhd.minipos.listItem.ItemViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val addItemRequestCode = 1
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        btnAddItem.setOnClickListener {
            showActivityAddItem()
        }

        btnListItem.setOnClickListener { showActivityListItem() }
    }

    private fun showActivityListItem() {
        startActivity(Intent(this, ActivityListItem::class.java))
    }

    private fun showActivityAddItem() {
        val intent = Intent(this, ActivityAddItem::class.java)
        startActivityForResult(intent, addItemRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) return

        if (requestCode == addItemRequestCode) {
            data?.let {
                val item = it.getSerializableExtra(ActivityAddItem.EXTRA_ITEM) as Item
                itemViewModel.insert(item)
            }

        }
    }
}
