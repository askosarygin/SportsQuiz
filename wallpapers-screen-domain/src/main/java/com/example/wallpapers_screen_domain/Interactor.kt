package com.example.wallpapers_screen_domain

import com.example.common.Wallpaper

interface Interactor {

    suspend fun getPointsFromAccountDataStorage(): Int

    suspend fun savePointsToAccountDataStorage(points: Int): Boolean

    suspend fun saveBoughtWallpaperIdToAccountDataStorage(id: Long): Boolean

    suspend fun checkBoughtWallpaperFromAccountDataStorage(id: Long): Boolean

    suspend fun loadWallpapersFromNet(): List<Wallpaper>
}