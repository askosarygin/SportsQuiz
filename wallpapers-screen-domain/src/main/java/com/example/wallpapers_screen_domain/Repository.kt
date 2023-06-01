package com.example.wallpapers_screen_domain

import android.graphics.Bitmap
import com.example.common.ResponseWallpapers

interface Repository {

    suspend fun downloadBitmapFromUrl(url: String): Bitmap

    suspend fun getPointsFromAccountDataStorage(): Int

    suspend fun savePointsToAccountDataStorage(points: Int): Boolean

    suspend fun loadWallpapersFromNet(): ResponseWallpapers

    suspend fun saveBoughtWallpaperIdToAccountDataStorage(id: Long) : Boolean

    suspend fun checkBoughtWallpaperFromAccountDataStorage(id: Long): Boolean
}