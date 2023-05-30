package com.example.data.db

import com.example.common.Difficult
import com.example.common.QuestionInfoDB

interface QuestionsInfoDBStorage {

    suspend fun save(questionInfoDB: QuestionInfoDB): Boolean

    suspend fun getAllWithDifficult(difficult: Difficult): List<QuestionInfoDB>
}