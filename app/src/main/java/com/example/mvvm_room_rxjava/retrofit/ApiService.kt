package com.example.mvvm_room_rxjava.retrofit

import com.example.mvvm_room_rxjava.retrofit.models.Response
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("api/?key=19472353-9ea6e174e748bcc3e5c4decd5&image_type=photo")
    fun getData(): Single<Response>
}