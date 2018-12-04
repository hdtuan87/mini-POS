package com.tuanhd.minipos.pay

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.POSDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PayViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PayRepository

    var item = MutableLiveData<Item>()
    var isItemExist = MutableLiveData<Boolean>()

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = PayRepository(itemDao)
    }

    fun checkItem(code: String){
        repository.getItem(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                it?.let {
                    item.value = it
                }
            }
    }

}