package com.example.main_screen_domain.usecases

import com.example.main_screen_domain.Repository
import javax.inject.Inject

class GetPointsFromAccountDataStorageUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(): Int = repository.getPointsFromAccountDataStorage()
}