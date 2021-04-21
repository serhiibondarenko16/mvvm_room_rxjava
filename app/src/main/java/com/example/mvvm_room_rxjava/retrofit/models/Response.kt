package com.example.mvvm_room_rxjava.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Response(
    @field:SerializedName("hits")
    val hits: List<HitsItem?>? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field: SerializedName("totalHits")
    val totalHits: Int? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(HitsItem.CREATOR),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(total)
        parcel.writeValue(totalHits)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Response> {
        override fun createFromParcel(parcel: Parcel): Response {
            return Response(parcel)
        }

        override fun newArray(size: Int): Array<Response?> {
            return arrayOfNulls(size)
        }
    }
}

data class HitsItem(

    @field:SerializedName("webformatHeight")
    val webformatHeight: Int? = null,

    @field:SerializedName("imageWidth")
    val imageWidth: Int? = null,

    @field:SerializedName("favorites")
    val favorites: Int? = null,

    @field:SerializedName("previewHeight")
    val previewHeight: Int? = null,

    @field:SerializedName("webformatURL")
    val webformatURL: String? = null,

    @field:SerializedName("userImageURL")
    val userImageURL: String? = null,

    @field:SerializedName("previewURL")
    val previewURL: String? = null,

    @field:SerializedName("comments")
    val comments: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("imageHeight")
    val imageHeight: Int? = null,

    @field:SerializedName("tags")
    val tags: String? = null,

    @field:SerializedName("previewWidth")
    val previewWidth: Int? = null,

    @field:SerializedName("downloads")
    val downloads: Int? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("largeImageURL")
    val largeImageURL: String? = null,

    @field:SerializedName("pageURL")
    val pageURL: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("imageSize")
    val imageSize: Int? = null,

    @field:SerializedName("webformatWidth")
    val webformatWidth: Int? = null,

    @field:SerializedName("user")
    val user: String? = null,

    @field:SerializedName("views")
    val views: Int? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(webformatHeight)
        parcel.writeValue(imageWidth)
        parcel.writeValue(favorites)
        parcel.writeValue(previewHeight)
        parcel.writeString(webformatURL)
        parcel.writeString(userImageURL)
        parcel.writeString(previewURL)
        parcel.writeValue(comments)
        parcel.writeString(type)
        parcel.writeValue(imageHeight)
        parcel.writeString(tags)
        parcel.writeValue(previewWidth)
        parcel.writeValue(downloads)
        parcel.writeValue(userId)
        parcel.writeString(largeImageURL)
        parcel.writeString(pageURL)
        parcel.writeValue(id)
        parcel.writeValue(imageSize)
        parcel.writeValue(webformatWidth)
        parcel.writeString(user)
        parcel.writeValue(views)
        parcel.writeValue(likes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HitsItem> {
        override fun createFromParcel(parcel: Parcel): HitsItem {
            return HitsItem(parcel)
        }

        override fun newArray(size: Int): Array<HitsItem?> {
            return arrayOfNulls(size)
        }
    }
}
