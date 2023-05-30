package com.example.common

data class QuestionInfoDB(
    val id: Long,
    val questionText: String,
    val correctAnswer: String,
    val incorrectAnswerOne: String,
    val incorrectAnswerTwo: String,
    val incorrectAnswerThree: String,
    val difficultyLevel: String
)