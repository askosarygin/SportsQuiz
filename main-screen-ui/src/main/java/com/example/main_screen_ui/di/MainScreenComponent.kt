package com.example.main_screen_ui.di

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.data.RepositoryMainScreenDomainImpl
import com.example.data.account_data.AccountDataStorage
import com.example.data.account_data.AccountDataStorageImpl
import com.example.main_screen_domain.Interactor
import com.example.main_screen_domain.InteractorImpl
import com.example.main_screen_domain.Repository
import com.example.main_screen_ui.screen_start.FragmentScreenStart
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
class MainScreenModule {
    @Provides
    fun provideCoroutineScopeIO(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
}

@Module
interface MainScreenModuleBinds {

    @Binds
    fun bindInteractorImplToInteractor(
        interactorImpl: InteractorImpl
    ): Interactor

    @Binds
    fun bindRepositoryImplToRepository(
        repositoryMainScreenDomainImpl: RepositoryMainScreenDomainImpl
    ): Repository

    @Binds
    fun bindAccountDataStorageImplToAccountDataStorage(
        accountDataStorageImpl: AccountDataStorageImpl
    ): AccountDataStorage
}

interface MainScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
    val resources: Resources
    val sharedPreferences: SharedPreferences
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