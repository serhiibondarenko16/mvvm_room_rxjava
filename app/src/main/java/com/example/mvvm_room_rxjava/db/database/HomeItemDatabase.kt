package com.example.mvvm_room_rxjava.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm_room_rxjava.db.dao.HomeItemDao
import com.example.mvvm_room_rxjava.db.entities.HomeItem

@Database(entities = [HomeItem::class], version = 1, exportSchema = false)
abstract class HomeItemDatabase : RoomDatabase() {

    abstract fun homeItemDao(): HomeItemDao

    companion object {

        @Volatile
        private var INSTANCE: HomeItemDatabase? = null

        fun getDatabase(context: Context): HomeItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HomeItemDatabase::class.java,
                    "db_item"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}