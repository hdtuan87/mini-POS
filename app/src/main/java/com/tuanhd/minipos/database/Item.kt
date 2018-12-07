package com.tuanhd.minipos.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Item(

    @PrimaryKey @ColumnInfo(name = "code")
    var code: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "thumb")
    var thumb: String,

    @ColumnInfo(name = "price")
    var price: Double

): Serializable