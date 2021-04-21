package com.example.mvvm_room_rxjava.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_room_rxjava.db.entities.HomeItem
import com.example.mvvm_room_rxjava.repository.ItemRepository

class HomeListViewModel(application: Application) : AndroidViewModel(application) {


    private lateinit var mHomeItems: MutableLiveData<List<HomeItem>>
    private var mItemRepository = ItemRepository
    private val TAG = "HomeListViewModel"

    init {
        run {
            if (this::mHomeItems.isInitialized) {
                Log.d(TAG, "mTest is already initialize")
                return@run
            }

            Log.d(TAG, "mTest initialize repository 1 time")
            // Init Database
            mItemRepository.initDb(application)

            // Get data from database
            mHomeItems = mItemRepository.getDataFromDb() as MutableLiveData<List<HomeItem>>
        }
    }

    fun makeInternetRequest(): LiveData<List<HomeItem>> {
        mHomeItems = mItemRepository.getDataFromInternet() as MutableLiveData<List<HomeItem>>
        return mHomeItems
    }

    fun getDbSize(): LiveData<Int> {
        return mItemRepository.getDbSize()
    }

    fun insertItemToDb(testItems: List<HomeItem>) {
        mItemRepository.insertToDb(testItems)
    }

    fun getDataFromViewModel(): LiveData<List<HomeItem>> {
        return mHomeItems
    }

}