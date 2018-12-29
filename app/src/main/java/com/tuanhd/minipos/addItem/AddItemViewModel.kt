package com.tuanhd.minipos.addItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.tuanhd.minipos.database.ItemRepository
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.POSDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
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

    private val repository: ItemRepository

    var item = MutableLiveData<Item>()

    private val findItemObserver: PublishSubject<String>
    private val disposableFindItem: Disposable

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)

        findItemObserver = PublishSubject.create()
        disposableFindItem = findItemObserver
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { repository.getItem(it)}
            .subscribe {
                it.subscribe { data ->
                    data?.let {
                        item.value = it
                    }
                }
            }

    }

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun delete(item: Item) = scope.launch(Dispatchers.IO) {
        repository.delete(item)
    }

    fun codeIsExist(code: String) {
        findItemObserver.onNext(code)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
        if (!disposableFindItem.isDisposed){
            disposableFindItem.dispose()
        }
    }
}