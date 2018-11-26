package com.tuanhd.minipos.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

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
}