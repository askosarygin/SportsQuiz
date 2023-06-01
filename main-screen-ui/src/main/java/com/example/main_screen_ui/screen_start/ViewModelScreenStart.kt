package com.example.main_screen_ui.screen_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelSingleLifeEvent
import com.example.main_screen_domain.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenStart(
    private val interactor: Interactor
) : SportsQuizViewModel<ViewModelScreenStart.Model>(Model()) {

    fun buttonNewGamePressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenDifficultySelection
            )
        )
    }

    fun buttonWallpapersStorePressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWallpapersStore
            )
        )
    }

    fun getPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            val points = interactor.getPointsFromAccountDataStorage()

            updatePoints(points)
        }
    }

    data class Model(
        val points: Int = 0,
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(navigateTo) {
            enum class NavigationDestination {
                ScreenDifficultySelection,
                ScreenWallpapersStore
            }
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationSingleLifeEvent) {
        update {
            it.copy(
                navigationEvent = navigationEvent
            )
        }
    }

    private fun updatePoints(points: Int) {
        update {
            it.copy(
                points = points
            )
        }
    }

    class Factory @Inject constructor(
        private val interactor: Interactor
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenStart::class.java)
            return ViewModelScreenStart(
                interactor
            ) as T
        }
    }
}