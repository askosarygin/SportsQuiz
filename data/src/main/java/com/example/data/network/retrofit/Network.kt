package com.example.data.network.retrofit

import com.example.data.network.retrofit.entities.nullable.ResponseWallpapersNullable

interface Network {
    suspend fun doRequestLoadWallpapers(): ResponseWallpapersNullable
}