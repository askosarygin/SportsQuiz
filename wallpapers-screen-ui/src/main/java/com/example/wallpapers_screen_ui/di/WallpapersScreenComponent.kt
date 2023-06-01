package com.example.wallpapers_screen_ui.di

import android.app.WallpaperManager
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.data.RepositoryWallpapersScreenDomainImpl
import com.example.data.account_data.AccountDataStorage
import com.example.data.account_data.AccountDataStorageImpl
import com.example.data.network.RepositoryNetwork
import com.example.data.network.retrofit.API
import com.example.wallpapers_screen_domain.Interactor
import com.example.wallpapers_screen_domain.InteractorImpl
import com.example.wallpapers_screen_domain.Repository
import com.example.wallpapers_screen_ui.screen_wallpaper.FragmentScreenWallpaper
import com.example.wallpapers_screen_ui.screen_wallpapers_store.FragmentScreenWallpapersStore
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Scope
import kotlin.properties.Delegates

@[WallpapersScreenScope Component(
    dependencies = [WallpapersScreenComponentDependencies::class],
    modules = [
        WallpapersScreenModule::class,
        WallpapersScreenModuleBinds::class
    ]
)]
internal interface WallpapersScreenComponent {

    fun inject(fragmentScreenWallpapersStore: FragmentScreenWallpapersStore)
    fun inject(fragmentScreenWallpaper: FragmentScreenWallpaper)

    @Component.Builder
    interface Builder {

        fun dependencies(wallpapersScreenComponentDependencies: WallpapersScreenComponentDependencies): Builder

        fun build(): WallpapersScreenComponent
    }
}

@Module
class WallpapersScreenModule

@Module
interface WallpapersScreenModuleBinds {

    @Binds
    fun bindRepositoryGameScreenDomainImplToRepository(
        repositoryWallpapersScreenDomainImpl: RepositoryWallpapersScreenDomainImpl
    ): Repository

    @Binds
    fun bindInteractorImplToInteractor(
        interactorImpl: InteractorImpl
    ): Interactor

    @Binds
    fun bindAccountDataStorageImplToAccountDataStorage(
        accountDataStorageImpl: AccountDataStorageImpl
    ): AccountDataStorage
}

interface WallpapersScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
    val resources: Resources
    val sharedPreferences: SharedPreferences
    val api: API
    val repositoryNetwork: RepositoryNetwork
    val wallpaperManager: WallpaperManager
}

object WallpapersScreenComponentDependenciesStore {
    var dependencies: WallpapersScreenComponentDependencies by Delegates.notNull()
}

internal class WallpapersScreenComponentViewModel : ViewModel() {

    init {
        wallpapersScreenComponent = createComponent()
    }

    companion object {
        private var wallpapersScreenComponent: WallpapersScreenComponent? = null

        private fun createComponent(): WallpapersScreenComponent = wallpapersScreenComponent
            ?: DaggerWallpapersScreenComponent
                .builder()
                .dependencies(WallpapersScreenComponentDependenciesStore.dependencies)
                .build()

        fun getComponent(): WallpapersScreenComponent = wallpapersScreenComponent
            ?: throw RuntimeException("WallpapersScreen component is not initialized")

        private fun closeComponent() {
            wallpapersScreenComponent = null
        }
    }

    override fun onCleared() {
        super.onCleared()

        closeComponent()
    }
}

@Scope
internal annotation class WallpapersScreenScope