package com.example.data.account_data

interface AccountDataStorage {
    fun savePoints(points: Int) : Boolean

    fun getPoints(): Int
}