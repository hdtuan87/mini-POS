package com.tuanhd.minipos.database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class ItemRepository(private val itemDao: ItemDao) {
    val allItem: LiveData<List<Item>> = itemDao.getAllItem()

    @WorkerThread
    fun insert(item: Item) {
        itemDao.insert(item)
    }
}