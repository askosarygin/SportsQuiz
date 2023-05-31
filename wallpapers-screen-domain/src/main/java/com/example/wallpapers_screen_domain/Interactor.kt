package com.example.wallpapers_screen_domain

interface Interactor {

    suspend fun getPointsFromAccountDataStorage(): Int

    suspend fun savePointsToAccountDataStorage(points: Int): Boolean
}