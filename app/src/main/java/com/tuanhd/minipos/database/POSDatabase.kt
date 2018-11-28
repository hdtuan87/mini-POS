package com.tuanhd.minipos.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Item::class], version = 1)
abstract class POSDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: POSDatabase? = null

        fun getDatabase(context: Context): POSDatabase {
            val temp = INSTANCE
            if (temp != null) return temp

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    POSDatabase::class.java,
                    "pos_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}