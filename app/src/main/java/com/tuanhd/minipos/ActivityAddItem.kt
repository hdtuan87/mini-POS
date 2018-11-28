package com.tuanhd.minipos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tuanhd.minipos.database.Item
import kotlinx.android.synthetic.main.activity_add_item.*
import java.util.*

class ActivityAddItem : AppCompatActivity() {

    companion object {
        const val extraItem = "extra_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        btnAddItem.setOnClickListener { makeItem() }
    }

    private fun makeItem() {
        val code = UUID.randomUUID().toString()
        val name = edtName.text.toString().trim()
        val price = edtPrice.text.toString().toDouble()
        val item = Item(code, name, price)

        val intent = Intent()
        intent.putExtra(extraItem, item)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}