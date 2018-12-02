package com.tuanhd.minipos.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Maybe

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItem(): LiveData<List<Item>>

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("DELETE FROM items")
    fun deleteAll()

    @Update
    fun update(item: Item)

    @Query("SELECT * FROM items WHERE code = (:code)")
    fun getItem(code: String): Maybe<Item>
}