package com.tuanhd.minipos.listItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.RoomDatabase
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemRepository
import com.tuanhd.minipos.database.POSDatabase
import kotlinx.coroutines.experimental.Job

class ItemViewModel(application: Application): AndroidViewModel(application){
    private var parentJob = Job()

    private val repository: ItemRepository

    val allItem: LiveData<List<Item>>

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        allItem = repository.allItem
    }

    fun insert(item: Item) = {
        repository.insert(item)
    }
}