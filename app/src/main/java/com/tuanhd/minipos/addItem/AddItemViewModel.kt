package com.tuanhd.minipos.addItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.POSDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddItemViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repository: AddItemRepository

    var item = MutableLiveData<Item>()

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = AddItemRepository(itemDao)
    }

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun codeIsExist(code: String) {
        repository.getItem(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                data?.let {
                    item.value = it
                }
            }.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}