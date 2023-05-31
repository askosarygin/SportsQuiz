package com.example.wallpapers_screen_domain

import com.example.wallpapers_screen_domain.usecases.GetPointsFromAccountDataStorageUseCase
import com.example.wallpapers_screen_domain.usecases.SavePointsToAccountDataStorageUseCase
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val getPointsFromAccountDataStorageUseCase: GetPointsFromAccountDataStorageUseCase,
    private val savePointsToAccountDataStorageUseCase: SavePointsToAccountDataStorageUseCase
) : Interactor {

    override suspend fun getPointsFromAccountDataStorage(): Int =
        getPointsFromAccountDataStorageUseCase.execute()

    override suspend fun savePointsToAccountDataStorage(points: Int): Boolean =
        savePointsToAccountDataStorageUseCase.execute(points)
}