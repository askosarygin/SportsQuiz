package com.example.data.account_data

interface AccountDataStorage {
    fun savePoints(points: Int) : Boolean

    fun getPoints(): Int

    fun saveBoughtWallpaperId(id: Long): Boolean

    fun checkBoughtWallpaper(id: Long) : Boolean
}