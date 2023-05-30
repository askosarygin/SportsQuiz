package com.example.game_screen_ui.screen_difficulty_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import javax.inject.Inject

class ViewModelScreenDifficultySelection : SportsQuizViewModel<ViewModelScreenDifficultySelection.Model>(Model()) {

    fun buttonEasyPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenGameEasy
            )
        )
    }

    fun buttonNormalPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenGameNormal
            )
        )
    }

    fun buttonHardPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenGameHard
            )
        )
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
    }

    data class Model(
        val navigationEvent: NavigationEvent? = null
    ) {
        class NavigationEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelEvent<NavigationEvent.NavigationDestination>(navigateTo) {
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