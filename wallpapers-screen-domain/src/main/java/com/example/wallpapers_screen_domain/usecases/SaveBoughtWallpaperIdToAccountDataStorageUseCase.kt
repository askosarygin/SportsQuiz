package com.example.wallpapers_screen_domain.usecases

import com.example.wallpapers_screen_domain.Repository
import javax.inject.Inject

class SaveBoughtWallpaperIdToAccountDataStorageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(id: Long): Boolean = repository.saveBoughtWallpaperIdToAccountDataStorage(id)
}