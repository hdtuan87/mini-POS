package com.tuanhd.minipos.pay

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemRepository
import com.tuanhd.minipos.database.POSDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PayViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository

    var item = MutableLiveData<Item>()

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
    }

    fun checkItem(code: String){
        repository.getItem(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                item.value = data
            }
    }

    fun calTotalAmount(data: ArrayList<Item>): Double{
        var totalAmount = 0.0
        for (item in data){
            totalAmount += item.price
        }

        return totalAmount
    }

}