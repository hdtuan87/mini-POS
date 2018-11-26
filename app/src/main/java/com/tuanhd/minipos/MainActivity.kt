package com.tuanhd.minipos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tuanhd.minipos.listItem.ActivityListItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddItem.setOnClickListener {
            showActivityAddItem()
        }

        btnListItem.setOnClickListener { showActivityListItem() }
    }

    private fun showActivityListItem() {
        startActivity(Intent(this, ActivityListItem::class.java))
    }

    private fun showActivityAddItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
