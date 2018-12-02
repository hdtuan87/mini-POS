package com.tuanhd.minipos.listItem

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemDao

class ItemRepository(private val itemDao: ItemDao) {
    val allItem: LiveData<List<Item>> = itemDao.getAllItem()

    @WorkerThread
    fun insert(item: Item) {
        itemDao.insert(item)
    }
}