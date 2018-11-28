package com.tuanhd.minipos.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Item::class], version = 1)
abstract class POSDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: POSDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): POSDatabase {
            val temp = INSTANCE
            if (temp != null) return temp

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    POSDatabase::class.java,
                    "pos_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(POSDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private class POSDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }

        private fun populateDatabase(itemDao: ItemDao){
            itemDao.deleteAll()

            val item = Item("alsjdflasdf", "Bim Bim", 5000.00)
            itemDao.insert(item)
        }
    }

}