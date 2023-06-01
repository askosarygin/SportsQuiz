package com.example.wallpapers_screen_domain.usecases

import com.example.common.Wallpaper
import com.example.wallpapers_screen_domain.Repository
import javax.inject.Inject

class LoadWallpapersFromNetUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun execute(): List<Wallpaper> {
        val responseWallpapers = repository.loadWallpapersFromNet().record.wallpapersStore

        return responseWallpapers.map {
            Wallpaper(
                it.id,
                it.name,
                it.price,
                repository.downloadBitmapFromUrl(it.imageUrl)
            )
        }
    }
}