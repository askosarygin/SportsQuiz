package com.example.wallpapers_screen_ui.di

import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.wallpapers_screen_ui.screen_wallpapers_store.FragmentScreenWallpapersStore
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

//    @Binds
//    fun bindInteractorImplToInteractor(
//        interactorImpl: InteractorImpl
//    ): Interactor
//
//    @Binds
//    fun bindRepositoryImplToRepository(
//        repositoryLoginDomainImpl: RepositoryLoginDomainImpl
//    ): Repository
//
//    @Binds
//    fun bindAccountDataLoaderImplToAccountDataLoader(
//        accountDataLoaderImpl: AccountDataLoaderImpl
//    ): AccountDataLoader
}

interface WallpapersScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
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