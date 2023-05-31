package com.example.main_screen_domain

import com.example.main_screen_domain.usecases.GetPointsFromAccountDataStorageUseCase
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val getPointsFromAccountDataStorageUseCase: GetPointsFromAccountDataStorageUseCase
) : Interactor {

    override suspend fun getPointsFromAccountDataStorage(): Int =
        getPointsFromAccountDataStorageUseCase.execute()
}