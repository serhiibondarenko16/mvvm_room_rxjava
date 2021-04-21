package com.example.mvvm_room_rxjava.db.dao

import androidx.room.*
import com.example.mvvm_room_rxjava.db.entities.HomeItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface HomeItemDao {

    @Query("SELECT * FROM db_item")
    fun getAll(): Flowable<List<HomeItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(homeItems: List<HomeItem>): Completable

    @Query("SELECT COUNT(*) FROM db_item")
    fun getDbSize(): Single<Int>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDb(homeItems: List<HomeItem>)

}