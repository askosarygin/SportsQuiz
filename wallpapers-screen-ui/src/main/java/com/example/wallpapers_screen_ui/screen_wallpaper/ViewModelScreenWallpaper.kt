package com.example.wallpapers_screen_ui.screen_wallpaper

import android.graphics.Bitmap
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

class ViewModelScreenWallpaper(
    private val interactor: Interactor
) : SportsQuizViewModel<ViewModelScreenWallpaper.Model>(Model()) {

    fun buyWallpaper() {
        viewModelScope.launch(Dispatchers.IO) {
            val pointsBalance = interactor.getPointsFromAccountDataStorage()
            val price = model.value.price
            if (pointsBalance - price >= 0) {
                interactor.saveBoughtWallpaperIdToAccountDataStorage(model.value.id)
                interactor.savePointsToAccountDataStorage((model.value.price * -1))
                updatePrice(-1)
                updateButtonBuyVisible(false)
                val newPointsBalance = interactor.getPointsFromAccountDataStorage()
                updatePoints(newPointsBalance)
            } else {
                updateNoPointsEvent(
                    Model.NoPointsSingleLifeEvent()
                )
            }
        }
    }

    fun setWallpaperInfo(wallpaper: Wallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkBought = interactor.checkBoughtWallpaperFromAccountDataStorage(wallpaper.id)
            if (!checkBought) {
                updatePrice(wallpaper.price)
            } else {
                updateButtonBuyVisible(false)
            }
            val pointsBalance = interactor.getPointsFromAccountDataStorage()
            updateId(wallpaper.id)
            updateName(wallpaper.name)
            updateImageResource(wallpaper.imageResource)
            updatePoints(pointsBalance)
        }
    }

    data class Model(
        val id: Long = 0L,
        val name: String = "",
        val price: Int = -1,
        val points: Int = 0,
        val imageResource: Bitmap? = null,
        val buttonBuyVisible: Boolean = true,
        val navigationEvent: NavigationSingleLifeEvent? = null,
        val noPointsEvent: NoPointsSingleLifeEvent? = null
    ) {
        class NavigationSingleLifeEvent(
            navigateTo: NavigationDestination
        ) : SportsQuizViewModelSingleLifeEvent<NavigationSingleLifeEvent.NavigationDestination>(
            navigateTo
        ) {
            enum class NavigationDestination {
                ScreenWallpaperStore
            }
        }

        class NoPointsSingleLifeEvent : SportsQuizViewModelSingleLifeEvent<String>("")
    }

    private fun updateNoPointsEvent(noPointsEvent: Model.NoPointsSingleLifeEvent) {
        update {
            it.copy(
                noPointsEvent = noPointsEvent
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

    private fun updateId(id: Long) {
        update {
            it.copy(
                id = id
            )
        }
    }

    private fun updateButtonBuyVisible(buttonBuyVisible: Boolean) {
        update {
            it.copy(
                buttonBuyVisible = buttonBuyVisible
            )
        }
    }

    private fun updateName(name: String) {
        update {
            it.copy(
                name = name
            )
        }
    }

    private fun updatePrice(price: Int) {
        update {
            it.copy(
                price = price
            )
        }
    }

    private fun updateImageResource(imageResource: Bitmap) {
        update {
            it.copy(
                imageResource = imageResource
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

    class Factory @Inject constructor(
        private val interactor: Interactor
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == ViewModelScreenWallpaper::class.java)
            return ViewModelScreenWallpaper(
                interactor
            ) as T
        }
    }
}