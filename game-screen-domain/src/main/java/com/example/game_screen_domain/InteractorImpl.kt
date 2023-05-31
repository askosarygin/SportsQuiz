package com.example.game_screen_domain

import com.example.common.QuestionInfo
import com.example.game_screen_domain.usecases.*
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val loadEasyQuestionsInfoFromDBUseCase: LoadEasyQuestionsInfoFromDBUseCase,
    private val loadNormalQuestionsInfoFromDBUseCase: LoadNormalQuestionsInfoFromDBUseCase,
    private val loadHardQuestionsInfoFromDBUseCase: LoadHardQuestionsInfoFromDBUseCase,
    private val getPointsFromAccountDataStorageUseCase: GetPointsFromAccountDataStorageUseCase,
    private val savePointsToAccountDataStorageUseCase: SavePointsToAccountDataStorageUseCase
) : Interactor {

    override suspend fun loadEasyQuestionsInfoFromDB(): List<QuestionInfo> =
        loadEasyQuestionsInfoFromDBUseCase.execute()

    override suspend fun loadNormalQuestionsInfoFromDB(): List<QuestionInfo> =
        loadNormalQuestionsInfoFromDBUseCase.execute()

    override suspend fun loadHardQuestionsInfoFromDB(): List<QuestionInfo> =
        loadHardQuestionsInfoFromDBUseCase.execute()

    override suspend fun getPointsFromAccountDataStorage(): Int =
        getPointsFromAccountDataStorageUseCase.execute()

    override suspend fun savePointsToAccountDataStorage(points: Int): Boolean =
        savePointsToAccountDataStorageUseCase.execute(points)
}