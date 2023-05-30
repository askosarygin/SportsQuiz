package com.example.game_screen_domain

import com.example.common.QuestionInfo
import com.example.game_screen_domain.usecases.LoadEasyQuestionsInfoFromDBUseCase
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val loadEasyQuestionsInfoFromDBUseCase: LoadEasyQuestionsInfoFromDBUseCase,
    private val loadNormalQuestionsInfoFromDBUseCase: LoadEasyQuestionsInfoFromDBUseCase,
    private val loadHardQuestionsInfoFromDBUseCase: LoadEasyQuestionsInfoFromDBUseCase
) : Interactor {

    override suspend fun loadEasyQuestionsInfoFromDB(): List<QuestionInfo> =
        loadEasyQuestionsInfoFromDBUseCase.execute()

    override suspend fun loadNormalQuestionsInfoFromDB(): List<QuestionInfo> =
        loadNormalQuestionsInfoFromDBUseCase.execute()

    override suspend fun loadHardQuestionsInfoFromDB(): List<QuestionInfo> =
        loadHardQuestionsInfoFromDBUseCase.execute()
}