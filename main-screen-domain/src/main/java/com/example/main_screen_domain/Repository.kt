package com.example.main_screen_domain

interface Repository {
    suspend fun getPointsFromAccountDataStorage(): Int
}