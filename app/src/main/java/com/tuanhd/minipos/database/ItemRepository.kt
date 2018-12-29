package com.tuanhd.minipos.database

import android.support.annotation.WorkerThread
import io.reactivex.Maybe

class ItemRepository(private val itemDao: ItemDao){

    @WorkerThread
    fun insert(item: Item){
        itemDao.insert(item)
    }

    @WorkerThread
    fun delete(item: Item){
        itemDao.delete(item)
    }

    @WorkerThread
    fun getItem(code: String): Maybe<Item>{
        return itemDao.getItem(code)
    }
}