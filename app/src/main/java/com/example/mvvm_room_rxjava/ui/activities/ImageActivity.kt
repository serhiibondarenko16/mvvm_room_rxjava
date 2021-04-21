package com.example.mvvm_room_rxjava.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm_room_rxjava.R

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_image_fragment)
        val imageView = findViewById<ImageView>(R.id.image_view)

        if (intent.hasExtra(getString(R.string.image_url))) {
            val imageUrl = intent.getStringExtra(getString(R.string.image_url))
            val requestOption = RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)

            Glide.with(constraintLayout)
                .applyDefaultRequestOptions(requestOption)
                .load(imageUrl)
                .into(imageView)
        }
    }
}