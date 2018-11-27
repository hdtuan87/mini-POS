package com.tuanhd.minipos.listItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.RoomDatabase
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.ItemRepository
import com.tuanhd.minipos.database.POSDatabase
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repository: ItemRepository

    val allItem: LiveData<List<Item>>

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        allItem = repository.allItem
    }

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}