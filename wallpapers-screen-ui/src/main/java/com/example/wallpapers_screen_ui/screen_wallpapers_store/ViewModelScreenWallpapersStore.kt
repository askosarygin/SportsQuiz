package com.example.wallpapers_screen_ui.screen_wallpapers_store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelSingleLifeEvent
import com.example.common.Wallpaper
import com.example.wallpapers_screen_domain.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenWallpapersStore(
    private val interactor: Interactor
) : SportsQuizViewModel<ViewModelScreenWallpapersStore.Model>(Model()) {

    init {
        loadWallpapers()
    }

    private fun loadWallpapers() {
        viewModelScope.launch(Dispatchers.IO) {
            val wallpapers = interactor.loadWallpapersFromNet()

            updateWallpapers(wallpapers)
        }
    }

    fun buttonBackPressed() {
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenStart
            )
        )
    }

    fun getPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            val points = interactor.getPointsFromAccountDataStorage()

            updatePoints(points)
        }
    }

    fun wallpaperSelected(position: Int) {
        updateSelectedWallpaper(model.value.wallpapers[position])
        updateNavigationEvent(
            Model.NavigationSingleLifeEvent(
                Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWallpaper
            )
        )
    }

    data class Model(
        val points: Int = 0,
        val wallpapers: List<Wallpaper> = listOf(),
        val selectedWallpaper: Wallpaper? = null,
        val navigationEvent: NavigationSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenStart,
                ScreenWallpaper
            }
        }
    }

    private fun updateSelectedWallpaper(selectedWallpaper: Wallpaper) {
        update {
            it.copy(
                selectedWallpaper = selectedWallpaper
            )
        }
    }

    private fun updateWallpapers(wallpapers: List<Wallpaper>) {
        update {
            it.copy(
                wallpapers = wallpapers
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
            require(modelClass == ViewModelScreenWallpapersStore::class.java)
            return ViewModelScreenWallpapersStore(
                interactor
            ) as T
        }
    }
}