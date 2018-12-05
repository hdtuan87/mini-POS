package com.tuanhd.minipos.listItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.POSDatabase

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    val allItems: LiveData<List<Item>>

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        allItems = itemDao.getAllItem()
    }

}