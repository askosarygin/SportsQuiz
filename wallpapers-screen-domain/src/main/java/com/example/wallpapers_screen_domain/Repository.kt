package com.example.wallpapers_screen_domain

interface Repository {

    suspend fun getPointsFromAccountDataStorage(): Int

    suspend fun savePointsToAccountDataStorage(points: Int): Boolean
}