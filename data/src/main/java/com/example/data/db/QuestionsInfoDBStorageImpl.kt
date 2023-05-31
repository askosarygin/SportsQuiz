package com.example.data.db

import android.util.Log
import com.example.common.Difficult
import com.example.common.QuestionInfoDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionsInfoDBStorageImpl @Inject constructor(
    private val questionsInfoDatabase: QuestionsInfoDAO
) : QuestionsInfoDBStorage {

    init {
        addQuestionsInfoToDB()
    }

    private fun addQuestionsInfoToDB() {
        GlobalScope.launch { //todo по идее вопросы должны подтягиваться из сети, а так имитация подтягивания из сети
            Log.i("MY_TAG", "Пошла загрузка данных в бд")
            val allQuestionsInfo = questionsInfoDatabase.getAll()

//            if (allQuestionsInfo.isEmpty()) {
                QuestionsForAddToDBService.listOfEasyQuestionsInfoWithIncorrectAnswers.forEach { questionInfo ->
                    save(questionInfo)
                }
                Log.i("MY_TAG", "Лёгкие вопросы загружены")

                QuestionsForAddToDBService.listOfNormalQuestionsInfoWithIncorrectAnswers.forEach { questionInfo ->
                    save(questionInfo)
                }
                Log.i("MY_TAG", "Средние вопросы загружены")

                QuestionsForAddToDBService.listOfHardQuestionsInfoWithIncorrectAnswers.forEach { questionInfo ->
                    save(questionInfo)
                }
                Log.i("MY_TAG", "Тяжелые вопросы загружены")
//            }
        }
    }

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

    override suspend fun getAllWithDifficult(difficult: Difficult): List<QuestionInfoDB> =
        questionsInfoDatabase.getAllWithDifficult(difficult.name).map {
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