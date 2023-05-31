package com.example.game_screen_ui.screen_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.QuestionInfo
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import com.example.game_screen_domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenGame(
    private val interactor: Interactor,
    private val coroutineScopeIO: CoroutineScope
) : SportsQuizViewModel<ViewModelScreenGame.Model>(Model()) {

    fun loadEasyQuestionsInfo() {
        coroutineScopeIO.launch {
            val questionsInfoLoaded = interactor.loadEasyQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    fun loadNormalQuestionsInfo() {
        coroutineScopeIO.launch {
            val questionsInfoLoaded = interactor.loadNormalQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    fun loadHardQuestionsInfo() {
        coroutineScopeIO.launch {
            val questionsInfoLoaded = interactor.loadHardQuestionsInfoFromDB()
            startGame(questionsInfoLoaded.shuffled())
        }
    }

    private fun startGame(questionsInfoLoaded: List<QuestionInfo>) {
        updateModel(
            questionsInfo = questionsInfoLoaded
        )
        updateQuestionInfoInModel(
            model.value.questionsInfo[model.value.currentQuestionNumber]
        )
        startTimer(5)
    }

    fun buttonPlayAgainPressed() {

    }

    private fun startTimer(seconds: Int) {
        coroutineScopeIO.launch {
            for (sec in seconds downTo 1) {
                if (sec < 10) {
                    updateModel(
                        timer = "0:0$sec"
                    )
                } else {
                    updateModel(
                        timer = "0:$sec"
                    )
                }

                delay(1000)
            }
            updateModel(
                gameStateEvent = Model.GameStateEvent(Model.GameStateEvent.GameState.Stop),
                timer = "0:00"
            )
        }
    }

    private fun updateQuestionInfoInModel(questionInfo: QuestionInfo) {
        when ((1..4).random()) {
            1 -> updateModel(
                question = questionInfo.questionText,
                answerOne = questionInfo.correctAnswer,
                answerTwo = questionInfo.incorrectAnswerOne,
                answerThree = questionInfo.incorrectAnswerTwo,
                answerFour = questionInfo.incorrectAnswerThree
            )
            2 -> updateModel(
                question = questionInfo.questionText,
                answerOne = questionInfo.incorrectAnswerOne,
                answerTwo = questionInfo.correctAnswer,
                answerThree = questionInfo.incorrectAnswerTwo,
                answerFour = questionInfo.incorrectAnswerThree
            )
            3 -> updateModel(
                question = questionInfo.questionText,
                answerOne = questionInfo.incorrectAnswerOne,
                answerTwo = questionInfo.incorrectAnswerTwo,
                answerThree = questionInfo.correctAnswer,
                answerFour = questionInfo.incorrectAnswerThree
            )
            4 -> updateModel(
                question = questionInfo.questionText,
                answerOne = questionInfo.incorrectAnswerOne,
                answerTwo = questionInfo.incorrectAnswerTwo,
                answerThree = questionInfo.incorrectAnswerThree,
                answerFour = questionInfo.correctAnswer
            )
        }
    }

    fun answerOneToggleSelection() {
        updateModel(answerOneSelected = !model.value.answerOneSelected)
        if (model.value.answerOneSelected) {
            updateModel(
                answerTwoSelected = false,
                answerThreeSelected = false,
                answerFourSelected = false,
                buttonNextQuestionVisible = true
            )
        } else {
            updateModel(buttonNextQuestionVisible = false)
        }
    }

    fun answerTwoToggleSelection() {
        updateModel(answerTwoSelected = !model.value.answerTwoSelected)
        if (model.value.answerTwoSelected) {
            updateModel(
                answerOneSelected = false,
                answerThreeSelected = false,
                answerFourSelected = false,
                buttonNextQuestionVisible = true
            )
        } else {
            updateModel(buttonNextQuestionVisible = false)
        }
    }

    fun answerThreeToggleSelection() {
        updateModel(answerThreeSelected = !model.value.answerThreeSelected)
        if (model.value.answerThreeSelected) {
            updateModel(
                answerOneSelected = false,
                answerTwoSelected = false,
                answerFourSelected = false,
                buttonNextQuestionVisible = true
            )
        } else {
            updateModel(buttonNextQuestionVisible = false)
        }
    }

    fun answerFourToggleSelection() {
        updateModel(answerFourSelected = !model.value.answerFourSelected)
        if (model.value.answerFourSelected) {
            updateModel(
                answerOneSelected = false,
                answerTwoSelected = false,
                answerThreeSelected = false,
                buttonNextQuestionVisible = true
            )
        } else {
            updateModel(buttonNextQuestionVisible = false)
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
        var points = model.value.points
        if (isAnswerCorrect()) {
            points += 1
        }
        updateModel(
            points = points,
            currentQuestionNumber = model.value.currentQuestionNumber + 1,
            answerOneSelected = false,
            answerTwoSelected = false,
            answerThreeSelected = false,
            answerFourSelected = false,
            buttonNextQuestionVisible = false
        )
        updateQuestionInfoInModel(model.value.questionsInfo[model.value.currentQuestionNumber])
    }

    private fun updateModel(
        question: String = model.value.question,
        answerOne: String = model.value.answerOne,
        answerTwo: String = model.value.answerTwo,
        answerThree: String = model.value.answerThree,
        answerFour: String = model.value.answerFour,
        answerOneSelected: Boolean = model.value.answerOneSelected,
        answerTwoSelected: Boolean = model.value.answerTwoSelected,
        answerThreeSelected: Boolean = model.value.answerThreeSelected,
        answerFourSelected: Boolean = model.value.answerFourSelected,
        questionsInfo: List<QuestionInfo> = model.value.questionsInfo,
        currentQuestionNumber: Int = model.value.currentQuestionNumber,
        navigationEvent: Model.NavigationEvent? = model.value.navigationEvent,
        gameStateEvent: Model.GameStateEvent? = model.value.gameStateEvent,
        buttonNextQuestionVisible: Boolean = model.value.buttonNextQuestionVisible,
        points: Int = model.value.points,
        timer: String = model.value.timer
    ) {
        update {
            it.copy(
                question = question,
                answerOne = answerOne,
                answerTwo = answerTwo,
                answerThree = answerThree,
                answerFour = answerFour,
                answerOneSelected = answerOneSelected,
                answerTwoSelected = answerTwoSelected,
                answerThreeSelected = answerThreeSelected,
                answerFourSelected = answerFourSelected,
                questionsInfo = questionsInfo,
                currentQuestionNumber = currentQuestionNumber,
                navigationEvent = navigationEvent,
                gameStateEvent = gameStateEvent,
                buttonNextQuestionVisible = buttonNextQuestionVisible,
                points = points,
                timer = timer
            )
        }
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
        val navigationEvent: NavigationEvent? = null,
        val gameStateEvent: GameStateEvent? = null,
        val buttonNextQuestionVisible: Boolean = false,
        val points: Int = 0,
        val timer: String = ""
    ) {
        class GameStateEvent(
            gameState: GameState
        ) : SportsQuizViewModelEvent<GameStateEvent.GameState>(gameState) {
            enum class GameState {
                Stop
            }
        }

        class NavigationEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelEvent<NavigationEvent.NavigationDestination>(navigateTo) {
            enum class NavigationDestination {
                ScreenDifficultySelection
            }
        }
    }

    class Factory @Inject constructor(
        private val interactor: Interactor,
        private val coroutineScopeIO: CoroutineScope,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenGame::class.java)
            return ViewModelScreenGame(
                interactor,
                coroutineScopeIO
            ) as T
        }
    }
}