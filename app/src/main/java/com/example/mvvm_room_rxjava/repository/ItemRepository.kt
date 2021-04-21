package com.example.mvvm_room_rxjava.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_room_rxjava.db.database.HomeItemDatabase
import com.example.mvvm_room_rxjava.db.entities.HomeItem
import com.example.mvvm_room_rxjava.retrofit.MyRetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


object ItemRepository {

    private val TAG = "ItemRepository"
    private lateinit var mHomeItemDatabase: HomeItemDatabase
    private var compositeDisposable = CompositeDisposable()

    fun initDb(context: Context) {
        Log.d(TAG, "call initDb()")

        mHomeItemDatabase = HomeItemDatabase.getDatabase(context)
    }

    fun cleanCompositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getDataFromDb(): LiveData<List<HomeItem>> {
        Log.d(TAG, "call getDataFromDb()")

        val liveDataTestItem = MutableLiveData<List<HomeItem>>()
        compositeDisposable.add(
            mHomeItemDatabase.homeItemDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "call getDataFromDb() finish ok")
                    liveDataTestItem.value = it
                }, {
                    Log.e(TAG, "getDataFromDb: Throwable: ${it.localizedMessage}")
                })
        )

        return liveDataTestItem
    }

    fun getDbSize(): LiveData<Int> {
        Log.d(TAG, "getDbSize() called")

        val dbSize = MutableLiveData<Int>()
        compositeDisposable.add(
            mHomeItemDatabase.homeItemDao().getDbSize()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dbSize.value = it
                }, {
                    Log.e(TAG, "dbSize: Throwable: ${it.localizedMessage}")
                })
        )

        return dbSize
    }

    fun insertToDb(testItems: List<HomeItem>) {
        Log.d(TAG, "insertToDb() called with: testItems = $testItems")

        compositeDisposable.add(
            mHomeItemDatabase.homeItemDao().insertAll(testItems)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "insertToDb onComplete")
                }, {
                    Log.e(TAG, "insertToDb Throwable: ${it.localizedMessage}")
                })
        )
    }


    fun getDataFromInternet(): LiveData<List<HomeItem>> {
        Log.d(TAG, "getDataFromInternet() called")

        val liveDataTestItem = MutableLiveData<List<HomeItem>>()
        compositeDisposable.add(
            MyRetrofitBuilder.buildService().getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "Repository getDataFromInternet: $it")
                    val testItems = ArrayList<HomeItem>()
                    if (it.hits != null) {
                        it.hits.forEach { hitsItem ->
                            val testItem = HomeItem(id = hitsItem?.id!!, hitsItem.largeImageURL)
                            testItems.add(testItem)
                        }
                    }
                    liveDataTestItem.value = testItems

                }, {
                    Log.e(TAG, "Repository getValueFromInternet error: ${it.localizedMessage}")
                })
        )

        return liveDataTestItem
    }
}