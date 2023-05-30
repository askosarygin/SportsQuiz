package com.example.data.db

import com.example.common.QuestionInfoDB

interface QuestionsInfoStorage {

    suspend fun save(questionInfoDB: QuestionInfoDB): Boolean

    suspend fun getAllEasyQuestionsInfo(): List<QuestionInfoDB>

    suspend fun getAllNormalQuestionsInfo(): List<QuestionInfoDB>

    suspend fun getAllHardQuestionsInfo(): List<QuestionInfoDB>
}