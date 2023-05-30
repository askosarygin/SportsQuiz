package com.example.wallpapers_screen_ui.screen_wallpapers_store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import javax.inject.Inject

class ViewModelScreenWallpapersStore : SportsQuizViewModel<ViewModelScreenWallpapersStore.Model>(Model()) {


    fun buttonBackPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenStart
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
                ScreenStart
            }
        }
    }

    class Factory @Inject constructor() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenWallpapersStore::class.java)
            return ViewModelScreenWallpapersStore() as T
        }
    }
}