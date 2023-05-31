package com.example.data.network.retrofit

import com.example.data.network.retrofit.entities.nullable.ResponseWallpapersNullable
import javax.inject.Inject

class NetworkImpl @Inject constructor(
    private val api: API
) : Network {

    override suspend fun doRequestLoadWallpapers(): ResponseWallpapersNullable {
        val networkResponse = api.loadWallpapers().execute()
        return checkNetworkResponse(networkResponse)
    }

    private fun <T> checkNetworkResponse(response: retrofit2.Response<T>): T {
        if (response.code() != 200) {
            throw RuntimeException("Network code ${response.code()}")
        }

        if (response.body() == null) {
            throw IllegalArgumentException("body = null\n\n${response.raw()}")
        }

        return response.body()!!
    }
}