package com.tuanhd.minipos.addItem

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.tuanhd.minipos.database.Item
import com.tuanhd.minipos.database.POSDatabase
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddItemViewModel(application: Application) : AndroidViewModel(application){
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repository: AddItemRepository

    init {
        val itemDao = POSDatabase.getDatabase(application).itemDao()
        repository = AddItemRepository(itemDao)
    }

    fun insert(item: Item) = scope.launch(Dispatchers.IO) {
        repository.insert(item)
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    inner class A : SingleObserver<Item>{
        override fun onSubscribe(d: Disposable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(e: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSuccess(t: Item) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}