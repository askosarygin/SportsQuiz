package com.example.data.network

import android.graphics.Bitmap
import com.example.common.ResponseWallpapers

interface RepositoryNetwork {

    suspend fun downloadBitmapFromUrl(url: String): Bitmap

    suspend fun loadWallpapersFromNet(): ResponseWallpapers
}