package com.example.main_screen_ui.screen_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import javax.inject.Inject

class ViewModelScreenStart : SportsQuizViewModel<ViewModelScreenStart.Model>(Model()) {

    fun buttonNewGamePressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenDifficultySelection
            )
        )
    }

    fun buttonWallpapersStorePressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenWallpapersStore
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
                ScreenDifficultySelection,
                ScreenWallpapersStore
            }
        }
    }

    class Factory @Inject constructor() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenStart::class.java)
            return ViewModelScreenStart() as T
        }
    }
}