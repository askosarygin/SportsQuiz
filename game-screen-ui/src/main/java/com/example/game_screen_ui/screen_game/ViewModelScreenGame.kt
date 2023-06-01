package com.example.game_screen_ui.screen_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.QuestionInfo
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelSingleLifeEvent
import com.example.game_screen_domain.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenGame(
    private val interactor: Interactor
) : SportsQuizViewModel<ViewModelScreenGame.Model>(Model()) {

    private val timeForGameSeconds = 20

    fun loadEasyQuestionsInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsInfoLoaded = interactor.loadEasyQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    fun loadNormalQuestionsInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsInfoLoaded = interactor.loadNormalQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    fun loadHardQuestionsInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val questionsInfoLoaded = interactor.loadHardQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    private fun startGame(questionsInfoLoaded: List<QuestionInfo>) {
        updateQuestionsInfo(questionsInfoLoaded)

        setQuestionAndAnswersWithRandom(
            model.value.questionsInfo[model.value.currentQuestionNumber]
        )

        startTimer(timeForGameSeconds)
    }

    private fun startTimer(seconds: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            for (sec in seconds downTo 1) {
                if (sec < 10) {
                    updateTimer(
                        timer = "0:0$sec"
                    )
                } else {
                    updateTimer(
                        timer = "0:$sec"
                    )
                }

                delay(1000)
            }
            updateGameStateEvent(Model.GameStateSingleLifeEvent(Model.GameStateSingleLifeEvent.GameState.Stop))
            updateTimer("0:00")
            interactor.savePointsToAccountDataStorage(model.value.earnedPoints)
        }
    }

    private fun setQuestionAndAnswersWithRandom(questionInfo: QuestionInfo) {
        when ((1..4).random()) {
            1 -> {
                updateQuestion(questionInfo.questionText)
                updateAnswerOne(questionInfo.correctAnswer)
                updateAnswerTwo(questionInfo.incorrectAnswerOne)
                updateAnswerThree(questionInfo.incorrectAnswerTwo)
                updateAnswerFour(questionInfo.incorrectAnswerThree)
            }
            2 -> {
                updateQuestion(questionInfo.questionText)
                updateAnswerOne(questionInfo.incorrectAnswerOne)
                updateAnswerTwo(questionInfo.correctAnswer)
                updateAnswerThree(questionInfo.incorrectAnswerTwo)
                updateAnswerFour(questionInfo.incorrectAnswerThree)
            }
            3 -> {
                updateQuestion(questionInfo.questionText)
                updateAnswerOne(questionInfo.incorrectAnswerOne)
                updateAnswerTwo(questionInfo.incorrectAnswerTwo)
                updateAnswerThree(questionInfo.correctAnswer)
                updateAnswerFour(questionInfo.incorrectAnswerThree)
            }
            4 -> {
                updateQuestion(questionInfo.questionText)
                updateAnswerOne(questionInfo.incorrectAnswerOne)
                updateAnswerTwo(questionInfo.incorrectAnswerTwo)
                updateAnswerThree(questionInfo.incorrectAnswerThree)
                updateAnswerFour(questionInfo.correctAnswer)
            }
        }
    }

    fun answerOneToggleSelection() {
        updateAnswerOneSelected(!model.value.answerOneSelected)
        if (model.value.answerOneSelected) {
            updateAnswerTwoSelected(false)
            updateAnswerThreeSelected(false)
            updateAnswerFourSelected(false)
            updateButtonNextQuestionVisible(true)
        } else {
            updateButtonNextQuestionVisible(false)
        }
    }

    fun answerTwoToggleSelection() {
        updateAnswerTwoSelected(!model.value.answerTwoSelected)
        if (model.value.answerTwoSelected) {
            updateAnswerOneSelected(false)
            updateAnswerThreeSelected(false)
            updateAnswerFourSelected(false)
            updateButtonNextQuestionVisible(true)
        } else {
            updateButtonNextQuestionVisible(false)
        }
    }

    fun answerThreeToggleSelection() {
        updateAnswerThreeSelected(!model.value.answerThreeSelected)
        if (model.value.answerThreeSelected) {
            updateAnswerOneSelected(false)
            updateAnswerTwoSelected(false)
            updateAnswerFourSelected(false)
            updateButtonNextQuestionVisible(true)
        } else {
            updateButtonNextQuestionVisible(false)
        }
    }

    fun answerFourToggleSelection() {
        updateAnswerFourSelected(answerFourSelected = !model.value.answerFourSelected)
        if (model.value.answerFourSelected) {
            updateAnswerOneSelected(false)
            updateAnswerTwoSelected(false)
            updateAnswerThreeSelected(false)
            updateButtonNextQuestionVisible(true)
        } else {
            updateButtonNextQuestionVisible(false)
        }
    }

    private fun isAnswerCorrect(): Boolean {
        if (model.value.answerOneSelected) {
            return model.value.answerOne == model.value.questionsInfo[model.value.currentQuestionNumber].correctAnswer
        }
        if (model.value.answerTwoSelected) {
            return model.value.answerTwo == model.value.questionsInfo[model.value.currentQuestionNumber].correctAnswer
        }
        if (model.value.answerThreeSelected) {
            return model.value.answerThree == model.value.questionsInfo[model.value.currentQuestionNumber].correctAnswer
        }
        if (model.value.answerFourSelected) {
            return model.value.answerFour == model.value.questionsInfo[model.value.currentQuestionNumber].correctAnswer
        }
        return false
    }

    fun buttonNextQuestionPressed() {
        var earnedPoints = model.value.earnedPoints
        if (isAnswerCorrect()) {
            earnedPoints += 1
        }

        updateEarnedPoints(earnedPoints)
        updateCurrentQuestionNumber(model.value.currentQuestionNumber + 1)
        updateAnswerOneSelected(false)
        updateAnswerTwoSelected(false)
        updateAnswerThreeSelected(false)
        updateAnswerFourSelected(false)
        updateButtonNextQuestionVisible(false)

        setQuestionAndAnswersWithRandom(model.value.questionsInfo[model.value.currentQuestionNumber])
    }

    data class Model(
        val question: String = "",
        val answerOne: String = "",
        val answerTwo: String = "",
        val answerThree: String = "",
        val answerFour: String = "",
        val answerOneSelected: Boolean = false,
        val answerTwoSelected: Boolean = false,
        val answerThreeSelected: Boolean = false,
        val answerFourSelected: Boolean = false,
        val questionsInfo: List<QuestionInfo> = listOf(),
        val currentQuestionNumber: Int = 0,
        val navigationEvent: NavigationSingleLifeEvent? = null,
        val gameStateEvent: GameStateSingleLifeEvent? = null,
        val buttonNextQuestionVisible: Boolean = false,
        val earnedPoints: Int = 0,
        val timer: String = ""
    ) {
        class GameStateSingleLifeEvent(
            gameState: GameState
        ) : SportsQuizViewModelSingleLifeEvent<GameStateSingleLifeEvent.GameState>(gameState) {
            enum class GameState {
                Stop
            }
        }

        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(navigateTo) {
            enum class NavigationDestination {
                ScreenDifficultySelection
            }
        }
    }

    private fun updateQuestion(question: String) {
        update {
            it.copy(
                question = question
            )
        }
    }

    private fun updateAnswerOne(answerOne: String) {
        update {
            it.copy(
                answerOne = answerOne
            )
        }
    }

    private fun updateAnswerTwo(answerTwo: String) {
        update {
            it.copy(
                answerTwo = answerTwo
            )
        }
    }

    private fun updateAnswerThree(answerThree: String) {
        update {
            it.copy(
                answerThree = answerThree
            )
        }
    }

    private fun updateAnswerFour(answerFour: String) {
        update {
            it.copy(
                answerFour = answerFour
            )
        }
    }

    private fun updateAnswerOneSelected(answerOneSelected: Boolean) {
        update {
            it.copy(
                answerOneSelected = answerOneSelected
            )
        }
    }

    private fun updateAnswerTwoSelected(answerTwoSelected: Boolean) {
        update {
            it.copy(
                answerTwoSelected = answerTwoSelected
            )
        }
    }

    private fun updateAnswerThreeSelected(answerThreeSelected: Boolean) {
        update {
            it.copy(
                answerThreeSelected = answerThreeSelected
            )
        }
    }

    private fun updateAnswerFourSelected(answerFourSelected: Boolean) {
        update {
            it.copy(
                answerFourSelected = answerFourSelected
            )
        }
    }

    private fun updateQuestionsInfo(questionsInfo: List<QuestionInfo>) {
        update {
            it.copy(
                questionsInfo = questionsInfo
            )
        }
    }

    private fun updateCurrentQuestionNumber(currentQuestionNumber: Int) {
        update {
            it.copy(
                currentQuestionNumber = currentQuestionNumber
            )
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
    }

    private fun updateGameStateEvent(gameStateEvent: Model.GameStateSingleLifeEvent) {
        update {
            it.copy(
                gameStateEvent = gameStateEvent
            )
        }
    }

    private fun updateButtonNextQuestionVisible(buttonNextQuestionVisible: Boolean) {
        update {
            it.copy(
                buttonNextQuestionVisible = buttonNextQuestionVisible
            )
        }
    }

    private fun updateEarnedPoints(earnedPoints: Int) {
        update {
            it.copy(
                earnedPoints = earnedPoints
            )
        }
    }


    private fun updateTimer(timer: String) {
        update {
            it.copy(
                timer = timer
            )
        }
    }

    class Factory @Inject constructor(
        private val interactor: Interactor
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenGame::class.java)
            return ViewModelScreenGame(
                interactor
            ) as T
        }
    }
}