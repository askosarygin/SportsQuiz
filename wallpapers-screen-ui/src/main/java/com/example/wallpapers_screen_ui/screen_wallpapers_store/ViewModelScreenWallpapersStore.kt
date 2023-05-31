package com.example.wallpapers_screen_ui.screen_wallpapers_store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import com.example.wallpapers_screen_domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenWallpapersStore(
    private val interactor: Interactor,
    private val coroutineScopeIO: CoroutineScope
) : SportsQuizViewModel<ViewModelScreenWallpapersStore.Model>(Model()) {


    fun buttonBackPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenStart
            )
        )
    }

    fun getPoints() {
        coroutineScopeIO.launch {
            val points = interactor.getPointsFromAccountDataStorage()

            updatePoints(points)
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
                ScreenStart
            }
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

    class Factory @Inject constructor(
        private val interactor: Interactor,
        private val coroutineScopeIO: CoroutineScope
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenWallpapersStore::class.java)
            return ViewModelScreenWallpapersStore(
                interactor,
                coroutineScopeIO
            ) as T
        }
    }
}