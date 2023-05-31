package com.example.data.account_data

interface AccountDataStorage {
    fun savePoints(points: Int)

    fun getPoints(): Int
}