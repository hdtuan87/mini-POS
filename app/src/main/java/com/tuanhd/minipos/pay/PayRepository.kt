package com.tuanhd.minipos.pay

import android.support.annotation.WorkerThread
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemDao
import io.reactivex.Maybe

class PayRepository(private val itemDao: ItemDao){

    @WorkerThread
    fun getItem(code: String): Maybe<Item>{
        return itemDao.getItem(code)
    }
}