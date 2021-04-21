package com.example.mvvm_room_rxjava.ui.fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvm_room_rxjava.R
import com.example.mvvm_room_rxjava.db.entities.HomeItem
import com.example.mvvm_room_rxjava.ui.activities.ImageActivity
import com.example.mvvm_room_rxjava.ui.adapters.HomeRecyclerAdapter
import com.example.mvvm_room_rxjava.ui.viewmodels.HomeListViewModel
import com.example.mvvm_room_rxjava.utils.isNetworkAvailable

class HomeListFragment :
    Fragment(R.layout.fragment_home_list),
    HomeRecyclerAdapter.OnImageListener {

    private val TAG = "HomeListFragment"
    private var mHomeItems = ArrayList<HomeItem>()
    private lateinit var mHomeAdapter: HomeRecyclerAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mViewModel: HomeListViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.homeListRecycler)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
    }

    private fun initRecyclerView() {
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mHomeAdapter = HomeRecyclerAdapter(mHomeItems, this@HomeListFragment)
            adapter = mHomeAdapter
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(HomeListViewModel::class.java)
        mViewModel.getDataFromViewModel().observe(requireActivity(), Observer { dataViewModel ->
            // If database value is null make internet request
            if (dataViewModel.isEmpty()) {
                Log.d(TAG, "ViewModel value is EMPTY load data from internet")
                updateFromInternet()
            } else {
                Log.d(TAG, "ViewModel value is not EMPTY, get value from ViewModel")
                changeValuesInAdapter(dataViewModel)
            }
        })

        mViewModel.getDbSize().observe(requireActivity(), Observer {
            Log.d(TAG, "ViewModel get DB size: $it")
        })

        initOnClickListener()
        initRecyclerView()
    }

    private fun changeValuesInAdapter(dataFromDb: List<HomeItem>) {
        mHomeItems.clear()
        mHomeItems.addAll(dataFromDb)
        mHomeAdapter.notifyDataSetChanged()
    }

    private fun initOnClickListener() {
        mSwipeRefreshLayout.setOnRefreshListener {
            updateFromInternet()
        }
    }

    private fun updateFromInternet() {
        Log.d(TAG, "updateFromInternet() called")

        if (isNetworkAvailable(requireContext())) {
            mSwipeRefreshLayout.isRefreshing = true
            Log.d(TAG, "Update date from internet")

            mViewModel.makeInternetRequest()
                .observe(requireActivity(), Observer { internet ->
                    Log.d(TAG, "updateFromInternet: internet: $internet")
                    Toast.makeText(
                        requireContext(),
                        "Update list from internet",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Get value from internet and update adapter
                    changeValuesInAdapter(internet)

                    // Save new data to db
                    mViewModel.insertItemToDb(internet)

                    mSwipeRefreshLayout.isRefreshing = false
                })
        } else {
            Log.d(TAG, "Please check internet connection")
            Toast.makeText(
                requireContext(),
                "Please check internet connection",
                Toast.LENGTH_SHORT
            ).show()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    // Open image in new Activity
    override fun onClickRecycler(position: Int) {
        val imageUrl = mHomeItems[position].largeImageURL
        val intent = Intent(requireContext(), ImageActivity::class.java)
        intent.putExtra(getString(R.string.image_url), imageUrl)
        startActivity(intent)
    }

}