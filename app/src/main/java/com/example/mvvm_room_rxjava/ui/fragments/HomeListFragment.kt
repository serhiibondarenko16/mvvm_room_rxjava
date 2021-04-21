package com.example.mvvm_room_rxjava.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.mvvm_room_rxjava.utils.showMessage

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
                updateFromInternet()
            } else {
                changeValuesInAdapter(dataViewModel)
            }
        })

        mViewModel.getDbSize().observe(requireActivity(), Observer {
            Log.d(TAG, "call onActivityCreated: ViewModel get DB size: $it")
        })

        initOnClickListener()
        initRecyclerView()
    }

    private fun changeValuesInAdapter(data: List<HomeItem>) {
        mHomeItems.clear()
        mHomeItems.addAll(data)
        mHomeAdapter.notifyDataSetChanged()
    }

    private fun initOnClickListener() {
        mSwipeRefreshLayout.setOnRefreshListener {
            updateFromInternet()
        }
    }

    private fun updateFromInternet() {
        if (isNetworkAvailable(requireContext())) {
            isRefreshing(true)

            mViewModel.makeInternetRequest()
                .observe(requireActivity(), Observer { dataFromInternet ->
                    showMessage(requireContext(), getString(R.string.update_data_from_internet))

                    // Get value from internet and update adapter
                    changeValuesInAdapter(dataFromInternet)

                    // Save new data to db
                    mViewModel.insertItemToDb(dataFromInternet)

                    isRefreshing(false)
                })
        } else {
            showMessage(requireContext(), getString(R.string.please_check_internet_connection))

            isRefreshing(false)
        }
    }

    // Open image in new Activity
    override fun onClickRecycler(position: Int) {
        val imageUrl = mHomeItems[position].largeImageURL
        val intent = Intent(requireContext(), ImageActivity::class.java)
        intent.putExtra(getString(R.string.image_url), imageUrl)
        startActivity(intent)
    }

    private fun isRefreshing(boolean: Boolean) {
        mSwipeRefreshLayout.isRefreshing = boolean
    }

}