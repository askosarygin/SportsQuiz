package com.example.game_screen_ui.screen_difficulty_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelSingleLifeEvent
import javax.inject.Inject

class ViewModelScreenDifficultySelection : SportsQuizViewModel<ViewModelScreenDifficultySelection.Model>(Model()) {

    fun buttonEasyPressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameEasy
            )
        )
    }

    fun buttonNormalPressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameNormal
            )
        )
    }

    fun buttonHardPressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenGameHard
            )
        )
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
    }

    data class Model(
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(navigateTo) {
            enum class NavigationDestination {
                ScreenGameEasy,
                ScreenGameNormal,
                ScreenGameHard
            }
        }
    }

    class Factory @Inject constructor() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenDifficultySelection::class.java)
            return ViewModelScreenDifficultySelection() as T
        }
    }
}