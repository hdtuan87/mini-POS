package com.tuanhd.minipos.addItem

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.listItem.ItemViewModel
import com.tuanhd.minipos.scanCode.BarcodeCaptureActivity
import io.reactivex.FlowableSubscriber
import io.reactivex.SingleObserver
import io.reactivex.internal.operators.flowable.FlowableObserveOn
import io.reactivex.internal.operators.flowable.FlowableSubscribeOn
import kotlinx.android.synthetic.main.activity_add_item.*

class ActivityAddItem : AppCompatActivity() {


    companion object {
        const val EXTRA_ITEM = "extra_item"
    }

    private val getCodeRequestCode = 1

    private lateinit var addItemViewModel: AddItemViewModel

    private var thumb = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)

        btnAddItem.setOnClickListener { makeItem() }

        btnScanCode.setOnClickListener { scanCode() }
    }

    private fun scanCode() {
        val intent = Intent(this, BarcodeCaptureActivity::class.java)
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true)
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false)
        startActivityForResult(intent, getCodeRequestCode)
    }

    private fun makeItem() {
        val code = txvCode.text.toString()
        val name = edtName.text.toString().trim()
        val price = edtPrice.text.toString().toDouble()
        val item = Item(code, name, thumb, price)

        addItemViewModel.insert(item)

        val intent = Intent()
        intent.putExtra(EXTRA_ITEM, item)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != CommonStatusCodes.SUCCESS) return

        data?.let {
            val barcode = it.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
            txvCode.text = barcode.displayValue
        }


    }


    private fun showCodeExist() {
        Snackbar.make(container, "Code da ton tai", Snackbar.LENGTH_SHORT).show()
    }
}