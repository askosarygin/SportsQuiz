package com.example.wallpapers_screen_ui.screen_wallpapers_store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.SportsQuizViewModel
import com.example.common.SportsQuizViewModelEvent
import com.example.common.Wallpaper
import com.example.wallpapers_screen_domain.Interactor
import com.example.wallpapers_screen_domain.usecases.LoadWallpapersFromNetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelScreenWallpapersStore(
    private val interactor: Interactor,
    private val loadWallpapersFromNetUseCase: LoadWallpapersFromNetUseCase
) : SportsQuizViewModel<ViewModelScreenWallpapersStore.Model>(Model()) {

    init {
        loadWallpapers() //todo тогда так же надо сделать выгрузку вопросов чтоб прям ваще по красоте
    }

    private fun loadWallpapers() {
        viewModelScope.launch(Dispatchers.IO) { //TODO надо так же сделать везде
            val wallpapers = loadWallpapersFromNetUseCase.execute()

            updateWallpapers(wallpapers)
        }
    }

    fun buttonBackPressed() {
        updateNavigationEvent(
            Model.NavigationEvent(
                Model.NavigationEvent.NavigationDestination.ScreenStart
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
        val wallpapers: List<Wallpaper> = listOf(),
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

    private fun updateWallpapers(wallpapers: List<Wallpaper>) {
        update {
            it.copy(
                wallpapers = wallpapers
            )
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
        private val loadWallpapersFromNetUseCase: LoadWallpapersFromNetUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenWallpapersStore::class.java)
            return ViewModelScreenWallpapersStore(
                interactor,
                loadWallpapersFromNetUseCase
            ) as T
        }
    }
}