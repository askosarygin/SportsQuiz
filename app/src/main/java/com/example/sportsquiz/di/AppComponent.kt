package com.example.sportsquiz.di

import com.example.common.NavHostsInfo
import com.example.game_screen_ui.di.GameScreenComponentDependencies
import com.example.main_screen_ui.di.MainScreenComponentDependencies
import com.example.sportsquiz.R
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentDependencies
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@[AppScope Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class
    ]
)]
interface AppComponent
    : GameScreenComponentDependencies,
    MainScreenComponentDependencies,
    WallpapersScreenComponentDependencies {

    override val navHostsInfo: NavHostsInfo

    @Component.Builder
    interface Builder {

//        @BindsInstance
//        fun context(): Builder

        fun build(): AppComponent
    }
}

@Module
class AppModule {

    @Provides
    fun provideNavHostsInfo(): NavHostsInfo = NavHostsInfo(
        globalNavHostId = R.id.global_fragment_container_view
    )

}

@Module
interface AppModuleBinds

@Scope
annotation class AppScope