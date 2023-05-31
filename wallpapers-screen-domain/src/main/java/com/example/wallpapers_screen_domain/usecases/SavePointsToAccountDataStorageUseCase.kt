package com.example.wallpapers_screen_domain.usecases

import com.example.wallpapers_screen_domain.Repository
import javax.inject.Inject

class SavePointsToAccountDataStorageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(points: Int): Boolean = repository.savePointsToAccountDataStorage(points)
}