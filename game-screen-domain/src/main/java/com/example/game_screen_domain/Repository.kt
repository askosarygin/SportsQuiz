package com.example.game_screen_domain

import com.example.common.Difficult
import com.example.common.QuestionInfoDB

interface Repository {
    suspend fun loadQuestionsInfoDBWithDifficult(difficult: Difficult): List<QuestionInfoDB>

    suspend fun getPointsFromAccountDataStorage(): Int

    suspend fun savePointsToAccountDataStorage(points: Int): Boolean
}