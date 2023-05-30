package com.example.data.db

import androidx.room.*

private const val TABLE_NAME_QUESTIONS_AND_ANSWERS = "questions_and_answers"
private const val QUESTION_TEXT = "question_text"
private const val CORRECT_ANSWER = "correct_answer"
private const val INCORRECT_ANSWER_ONE = "incorrect_answer_one"
private const val INCORRECT_ANSWER_TWO = "incorrect_answer_two"
private const val INCORRECT_ANSWER_THREE = "incorrect_answer_three"
private const val DIFFICULTY_LEVEL = "difficulty_level"

private const val EASY = "easy"
private const val NORMAL = "normal"
private const val HARD = "hard"



@Entity(tableName = TABLE_NAME_QUESTIONS_AND_ANSWERS)
data class QuestionInfoDatabaseClass(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = QUESTION_TEXT)val questionText: String,
    @ColumnInfo(name = CORRECT_ANSWER)val correctAnswer: String,
    @ColumnInfo(name = INCORRECT_ANSWER_ONE)val incorrectAnswerOne: String,
    @ColumnInfo(name = INCORRECT_ANSWER_TWO)val incorrectAnswerTwo: String,
    @ColumnInfo(name = INCORRECT_ANSWER_THREE)val incorrectAnswerThree: String,
    @ColumnInfo(name = DIFFICULTY_LEVEL)val difficultyLevel: String
)

@Dao
interface QuestionsInfoDAO {

    @Insert
    fun add(questionInfoDatabaseClass: QuestionInfoDatabaseClass)

    @Query("SELECT * FROM $TABLE_NAME_QUESTIONS_AND_ANSWERS")
    fun getAll(): List<QuestionInfoDatabaseClass>

    @Query("SELECT * FROM $TABLE_NAME_QUESTIONS_AND_ANSWERS WHERE $DIFFICULTY_LEVEL='$EASY'")
    fun getAllEasy(): List<QuestionInfoDatabaseClass>

    @Query("SELECT * FROM $TABLE_NAME_QUESTIONS_AND_ANSWERS WHERE $DIFFICULTY_LEVEL='$NORMAL'")
    fun getAllNormal(): List<QuestionInfoDatabaseClass>

    @Query("SELECT * FROM $TABLE_NAME_QUESTIONS_AND_ANSWERS WHERE $DIFFICULTY_LEVEL='$HARD'")
    fun getAllHard(): List<QuestionInfoDatabaseClass>
}

@Database(entities = [QuestionInfoDatabaseClass::class], version = 1, exportSchema = false)
abstract class QuestionsInfoDatabase : RoomDatabase() {
    abstract fun questionsInfoDAO(): QuestionsInfoDAO
}

