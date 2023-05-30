package com.example.main_screen_ui.di

import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.main_screen_ui.screen_start.FragmentScreenStart
import dagger.Component
import dagger.Module
import javax.inject.Scope
import kotlin.properties.Delegates

@[MainScreenScope Component(
    dependencies = [MainScreenComponentDependencies::class],
    modules = [
        MainScreenModule::class,
        MainScreenModuleBinds::class
    ]
)]
internal interface MainScreenComponent {

    fun inject(fragmentScreenStart: FragmentScreenStart)

    @Component.Builder
    interface Builder {

        fun dependencies(mainScreenComponentDependencies: MainScreenComponentDependencies): Builder

        fun build(): MainScreenComponent
    }
}

@Module
class MainScreenModule

@Module
interface MainScreenModuleBinds {

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

interface MainScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
}

object MainScreenComponentDependenciesStore {
    var dependencies: MainScreenComponentDependencies by Delegates.notNull()
}

internal class MainScreenComponentViewModel : ViewModel() {

    init {
        mainScreenComponent = createComponent()
    }

    companion object {
        private var mainScreenComponent: MainScreenComponent? = null

        private fun createComponent(): MainScreenComponent = mainScreenComponent
            ?: DaggerMainScreenComponent
                .builder()
                .dependencies(MainScreenComponentDependenciesStore.dependencies)
                .build()

        fun getComponent(): MainScreenComponent = mainScreenComponent
            ?: throw RuntimeException("MainScreen component is not initialized")

        private fun closeComponent() {
            mainScreenComponent = null
        }
    }

    override fun onCleared() {
        super.onCleared()

        closeComponent()
    }
}

@Scope
internal annotation class MainScreenScope