package com.tuanhd.minipos.addItem

import android.support.annotation.WorkerThread
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemDao
import io.reactivex.Maybe

class AddItemRepository(private val itemDao: ItemDao){

    @WorkerThread
    fun insert(item: Item){
        itemDao.insert(item)
    }

    @WorkerThread
    fun getItem(code: String): Maybe<Item>{
        return itemDao.getItem(code)
    }
}