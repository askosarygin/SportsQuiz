package com.example.main_screen_ui.screen_start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import com.example.main_screen_domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenStart(
    private val interactor: Interactor,
    private val coroutineScopeIO: CoroutineScope
) : SportsQuizViewModel<ViewModelScreenStart.Model>(Model()) {

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

    fun getPoints() {
        coroutineScopeIO.launch {
            val points = interactor.getPointsFromAccountDataStorage()

            updatePoints(points)
        }
    }

    private fun updateNavigationEvent(navigationEvent: Model.NavigationEvent) {
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

    data class Model(
        val points: Int = 0,
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

    class Factory @Inject constructor(
        private val interactor: Interactor,
        private val coroutineScopeIO: CoroutineScope
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenStart::class.java)
            return ViewModelScreenStart(
                interactor,
                coroutineScopeIO
            ) as T
        }
    }
}