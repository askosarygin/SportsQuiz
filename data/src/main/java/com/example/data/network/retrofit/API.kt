package com.example.data.network.retrofit

import com.example.data.network.retrofit.entities.nullable.ResponseWallpapersNullable
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("6477aa6a8e4aa6225ea74e56")
    fun loadWallpapers(): Call<ResponseWallpapersNullable>
}