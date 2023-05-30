package com.example.game_screen_domain.usecases

import com.example.common.Difficult
import com.example.common.QuestionInfo
import com.example.game_screen_domain.Repository
import javax.inject.Inject

class LoadHardQuestionsInfoFromDBUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(): List<QuestionInfo> = repository
        .loadQuestionsInfoDBWithDifficult(Difficult.Hard)
        .map {
            QuestionInfo(
                it.questionText,
                it.correctAnswer,
                it.incorrectAnswerOne,
                it.incorrectAnswerTwo,
                it.incorrectAnswerThree,
            )
        }
}