package com.tuanhd.minipos.addItem

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.tuanhd.minipos.R
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.scanCode.BarcodeCaptureActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_item.*
import java.util.concurrent.TimeUnit

class ActivityAddItem : AppCompatActivity() {


    companion object {
        const val EXTRA_ITEM = "extra_item"
    }

    private val getCodeRequestCode = 1

    private lateinit var addItemViewModel: AddItemViewModel

    private var mItem = Item("", "", "", 0.0)

    private lateinit var disposableBtnAdd: Disposable
    private lateinit var disposableBtnScan: Disposable
    private lateinit var disposableEdtName: Disposable
    private lateinit var disposableEdtPrice: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        addItemViewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        addItemViewModel.item.observe(this, Observer { data ->
            data?.let {
                showCodeExist(it)
            }
        })

        disposableBtnAdd = RxView.clicks(btnAddItem).subscribe {
            makeItem()
        }

        disposableBtnScan = RxView.clicks(btnScanCode).subscribe {
            scanCode()
        }

        disposableEdtName = RxTextView.textChanges(edtName)
            .debounce(350, TimeUnit.MILLISECONDS)
            .subscribe { name ->
                mItem.name = name.toString()
            }

        disposableEdtPrice = RxTextView.textChanges(edtPrice)
            .debounce(350, TimeUnit.MILLISECONDS)
            .subscribe { price ->
                if (price.isEmpty()) return@subscribe

                mItem.price = price.toString().toDouble()
            }

        scanCode()
    }

    private fun scanCode() {
        val intent = Intent(this, BarcodeCaptureActivity::class.java)
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true)
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false)
        startActivityForResult(intent, getCodeRequestCode)
    }

    private fun loadDataToUI(item: Item) {
        txvCode.text = item.code
        edtName.setText(item.name)
        edtPrice.setText(item.price.toString())
    }

    private fun makeItem() {

        addItemViewModel.insert(mItem)

        val intent = Intent()
        intent.putExtra(EXTRA_ITEM, mItem)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != CommonStatusCodes.SUCCESS) return

        data?.let {
            val barcode = it.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
            txvCode.text = barcode.displayValue
            addItemViewModel.codeIsExist(barcode.displayValue)
        }


    }


    private fun showCodeExist(item: Item) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Code da ton tai. co muon sua item khong?")
        dialog.setNegativeButton(R.string.edit) { _, _ ->
            mItem = item
            loadDataToUI(item)
        }

        dialog.setPositiveButton(R.string.cancel, null)
        dialog.show()
    }

    override fun onDestroy() {
        if (!disposableBtnAdd.isDisposed) {
            disposableBtnAdd.dispose()
        }

        if (!disposableBtnScan.isDisposed) {
            disposableBtnScan.dispose()
        }

        if (!disposableEdtName.isDisposed) {
            disposableEdtName.dispose()
        }

        if (!disposableEdtPrice.isDisposed) {
            disposableEdtPrice.dispose()
        }

        super.onDestroy()
    }
}