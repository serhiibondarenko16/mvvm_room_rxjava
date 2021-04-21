package com.example.mvvm_room_rxjava.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm_room_rxjava.R
import com.example.mvvm_room_rxjava.db.entities.HomeItem

class HomeRecyclerAdapter(
    private val items: List<HomeItem>,
    private val listener: OnImageListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_activity_list, parent, false)
        return HomeViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> {
                holder.bind(homeItem = items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class HomeViewHolder constructor(
        itemView: View,
        private val listener: OnImageListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image = itemView.findViewById<ImageView>(R.id.item_list_image)

        fun bind(homeItem: HomeItem) {
            itemView.setOnClickListener(this)

            val requestOption = RequestOptions
                .placeholderOf(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .override(1000, 1000)

            Glide.with(itemView)
                .applyDefaultRequestOptions(requestOption)
                .load(homeItem.largeImageURL)
                .into(image)
        }

        override fun onClick(v: View?) {
            listener.onClickRecycler(absoluteAdapterPosition)
        }
    }

    interface OnImageListener {
        fun onClickRecycler(position: Int)
    }
}