package com.example.main_screen_domain

interface Interactor {
    suspend fun getPointsFromAccountDataStorage(): Int
}