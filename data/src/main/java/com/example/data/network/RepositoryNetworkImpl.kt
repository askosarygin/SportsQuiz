package com.example.data.network

import android.graphics.Bitmap
import com.bumptech.glide.RequestManager
import com.example.common.ResponseWallpapers
import com.example.data.network.retrofit.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryNetworkImpl @Inject constructor(
    private val network: Network,
    private val loadBitmap: RequestManager
) : RepositoryNetwork {

    override suspend fun downloadBitmapFromUrl(url: String): Bitmap {
        return withContext(Dispatchers.IO) {
            loadBitmap.asBitmap().load(url).submit().get()
        }
    }

    override suspend fun loadWallpapersFromNet(): ResponseWallpapers {
        val response = network.doRequestLoadWallpapers()

        val wallpapers = response.recordNullable!!.wallpapersStoreNullable!!.map {
            ResponseWallpapers.Record.WallpapersStore(
                it!!.name!!,
                it.price!!,
                it.imageUrl!!
            )
        }

        val metadata = ResponseWallpapers.Metadata(
            response.metadataNullable!!.name!!,
            response.metadataNullable.private!!,
            response.metadataNullable.createdAt!!,
        )

        return ResponseWallpapers(
            ResponseWallpapers.Record(
                wallpapers
            ),
            metadata
        )
    }
}