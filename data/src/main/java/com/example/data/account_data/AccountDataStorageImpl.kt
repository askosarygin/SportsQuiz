package com.example.data.account_data

import android.content.SharedPreferences
import android.content.res.Resources
import javax.inject.Inject

class AccountDataStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val resources: Resources
) : AccountDataStorage {
    private val keyPoints = resources.getString(com.example.common.R.string.key_points)

    override fun savePoints(points: Int) : Boolean {
        val savedPoints = getPoints()
        sharedPreferences.edit().putInt(keyPoints, points + savedPoints).apply()
        return true
    }

    override fun getPoints(): Int = sharedPreferences.getInt(keyPoints, 0)
}