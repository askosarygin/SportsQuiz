package com.example.wallpapers_screen_domain

import com.example.common.Wallpaper
import com.example.wallpapers_screen_domain.usecases.*
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val getPointsFromAccountDataStorageUseCase: GetPointsFromAccountDataStorageUseCase,
    private val savePointsToAccountDataStorageUseCase: SavePointsToAccountDataStorageUseCase,
    private val saveBoughtWallpaperIdToAccountDataStorageUseCase: SaveBoughtWallpaperIdToAccountDataStorageUseCase,
    private val checkBoughtWallpaperFromAccountDataStorageUseCase: CheckBoughtWallpaperFromAccountDataStorageUseCase,
    private val loadWallpapersFromNetUseCase: LoadWallpapersFromNetUseCase
) : Interactor {

    override suspend fun getPointsFromAccountDataStorage(): Int =
        getPointsFromAccountDataStorageUseCase.execute()

    override suspend fun savePointsToAccountDataStorage(points: Int): Boolean =
        savePointsToAccountDataStorageUseCase.execute(points)

    override suspend fun saveBoughtWallpaperIdToAccountDataStorage(id: Long): Boolean =
        saveBoughtWallpaperIdToAccountDataStorageUseCase.execute(id)

    override suspend fun checkBoughtWallpaperFromAccountDataStorage(id: Long): Boolean =
        checkBoughtWallpaperFromAccountDataStorageUseCase.execute(id)

    override suspend fun loadWallpapersFromNet(): List<Wallpaper> =
        loadWallpapersFromNetUseCase.execute()


}