package com.tuanhd.minipos.pay

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.listItem.AdapterItem
import com.tuanhd.minipos.scanCode.BarcodeCaptureActivity
import kotlinx.android.synthetic.main.activity_pay.*

class ActivityPay: AppCompatActivity(){
    private val getCodeRequestCode = 1

    private lateinit var payViewModel: PayViewModel
    private var items = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val adapter = AdapterItem(this)

        listItem.adapter = adapter
        listItem.layoutManager = LinearLayoutManager(this)
        adapter.addAll(items)


        payViewModel = ViewModelProviders.of(this).get(PayViewModel::class.java)
        payViewModel.item.observe(this, Observer { data ->
            data?.let {
                items.add(it)
                adapter.notifyDataSetChanged()
                calTotalAmount(items)
            }
        })

        payViewModel.isItemExist.observe(this, Observer {it ->
            it?.let {
                if (!it){
                    showNotFoudItem()
                }
            }
        })

        btnScanCode.setOnClickListener{showActivityScanBarcode()}

        showActivityScanBarcode()
    }

    private fun calTotalAmount(items: ArrayList<Item>) {
        var totalAmount = 0.0
        for (item in items){
            totalAmount += item.price
        }

        txvTotalAmount.text = totalAmount.toString()
    }

    private fun showNotFoudItem() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Khong tim thay item")
        dialog.setPositiveButton(R.string.cancel, null)
        dialog.show()
    }

    private fun showActivityScanBarcode() {
        val intent = Intent(this, BarcodeCaptureActivity::class.java)
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true)
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false)
        startActivityForResult(intent, getCodeRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != CommonStatusCodes.SUCCESS) return

        data?.let {
            val barcode = it.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
            payViewModel.checkItem(barcode.displayValue)
        }


    }
}