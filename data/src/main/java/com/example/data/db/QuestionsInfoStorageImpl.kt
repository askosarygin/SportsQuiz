package com.example.data.db

import com.example.common.QuestionInfoDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionsInfoStorageImpl @Inject constructor(
    private val questionsInfoDB: QuestionsInfoDAO
) : QuestionsInfoStorage {

    init {
//        addQuestionsInfoToDB() //TODO раскомментить когда будет готово к загрузке в БД
    }

    private fun addQuestionsInfoToDB() {
        GlobalScope.launch { //todo по идее вопросы должны подтягиваться из сети, а так имитация подтягивания из сети

            val allQuestionsInfo = questionsInfoDB.getAll()

            if (allQuestionsInfo.isEmpty()) {
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

        val allQuestionsInfo = questionsInfoDB.getAll()

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

        questionsInfoDB.add(newQuestionInfoDatabaseClass)
        return true
    }

    override suspend fun getAllEasyQuestionsInfo(): List<QuestionInfoDB> =
        questionsInfoDB.getAllEasy().map {
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

    override suspend fun getAllNormalQuestionsInfo(): List<QuestionInfoDB> =
        questionsInfoDB.getAllNormal().map {
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

    override suspend fun getAllHardQuestionsInfo(): List<QuestionInfoDB> =
        questionsInfoDB.getAllHard().map {
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