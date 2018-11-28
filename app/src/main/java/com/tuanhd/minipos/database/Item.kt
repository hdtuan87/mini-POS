package com.tuanhd.minipos.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "thumb")
    val thumb: String,

    @ColumnInfo(name = "price")
    val price: Double

): Serializable