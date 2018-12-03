package com.tuanhd.minipos.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Maybe
import io.reactivex.annotations.NonNull

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItem(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(@NonNull item: Item)

    @Delete
    fun delete(@NonNull item: Item)

    @Query("DELETE FROM items")
    fun deleteAll()

    @Update
    fun update(@NonNull item: Item)

    @Query("SELECT * FROM items WHERE code = (:code) LIMIT 1")
    fun getItem(@NonNull code: String): Maybe<Item>
}