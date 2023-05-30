package com.example.game_screen_domain

import com.example.common.QuestionInfo

interface Interactor {
    suspend fun loadEasyQuestionsInfoFromDB(): List<QuestionInfo>

    suspend fun loadNormalQuestionsInfoFromDB(): List<QuestionInfo>

    suspend fun loadHardQuestionsInfoFromDB(): List<QuestionInfo>
}