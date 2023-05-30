package com.example.data

import com.example.common.Difficult
import com.example.common.QuestionInfoDB
import com.example.data.db.QuestionsInfoDBStorage
import com.example.game_screen_domain.Repository
import javax.inject.Inject

class RepositoryGameScreenDomainImpl @Inject constructor(
    private val questionsInfoDBStorage: QuestionsInfoDBStorage
) : Repository {
    override suspend fun loadQuestionsInfoDBWithDifficult(difficult: Difficult): List<QuestionInfoDB> =
        questionsInfoDBStorage.getAllWithDifficult(difficult)
}