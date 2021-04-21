package com.example.mvvm_room_rxjava.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "db_item")
data class HomeItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String? = null
)
