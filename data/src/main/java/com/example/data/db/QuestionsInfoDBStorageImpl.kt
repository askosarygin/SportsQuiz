package com.example.data.db

import com.example.common.Difficult
import com.example.common.QuestionInfoDB
import javax.inject.Inject

class QuestionsInfoDBStorageImpl @Inject constructor(
    private val questionsInfoDatabase: QuestionsInfoDAO
) : QuestionsInfoDBStorage {

    override suspend fun save(questionInfoDB: QuestionInfoDB): Boolean {
        val newQuestionInfoDatabaseClass = QuestionInfoDatabaseClass(
            0L,
            questionInfoDB.questionText,
            questionInfoDB.correctAnswer,
            questionInfoDB.incorrectAnswerOne,
            questionInfoDB.incorrectAnswerTwo,
            questionInfoDB.incorrectAnswerThree,
            questionInfoDB.difficultyLevel
        )

        val allQuestionsInfo = questionsInfoDatabase.getAll()

        allQuestionsInfo.forEach {
            if (it.questionText == newQuestionInfoDatabaseClass.questionText &&
                it.correctAnswer == newQuestionInfoDatabaseClass.correctAnswer &&
                it.incorrectAnswerOne == newQuestionInfoDatabaseClass.incorrectAnswerOne &&
                it.incorrectAnswerTwo == newQuestionInfoDatabaseClass.incorrectAnswerTwo &&
                it.incorrectAnswerThree == newQuestionInfoDatabaseClass.incorrectAnswerThree &&
                it.difficultyLevel == newQuestionInfoDatabaseClass.difficultyLevel
            ) {
                return false
            }
        }

        questionsInfoDatabase.add(newQuestionInfoDatabaseClass)
        return true
    }

    override suspend fun getAllWithDifficult(difficult: Difficult): List<QuestionInfoDB> {
        if (questionsInfoDatabase.getAll().isEmpty()) {
            QuestionsForAddToDBService.listOfEasyQuestionsInfo.forEach { questionInfo ->
                save(questionInfo)
            }

            QuestionsForAddToDBService.listOfNormalQuestionsInfo.forEach { questionInfo ->
                save(questionInfo)
            }

            QuestionsForAddToDBService.listOfHardQuestionsInfo.forEach { questionInfo ->
                save(questionInfo)
            }
        }

        return questionsInfoDatabase.getAllWithDifficult(difficult.name).map {
            QuestionInfoDB(
                it.id,
                it.questionText,
                it.correctAnswer,
                it.incorrectAnswerOne,
                it.incorrectAnswerTwo,
                it.incorrectAnswerThree,
                it.difficultyLevel
            )
        }
    }
}