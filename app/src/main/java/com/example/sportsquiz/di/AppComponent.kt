package com.example.sportsquiz.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.room.Room
import com.example.common.NavHostsInfo
import com.example.data.db.QuestionsInfoDAO
import com.example.data.db.QuestionsInfoDBStorage
import com.example.data.db.QuestionsInfoDBStorageImpl
import com.example.data.db.QuestionsInfoDatabase
import com.example.game_screen_ui.di.GameScreenComponentDependencies
import com.example.main_screen_ui.di.MainScreenComponentDependencies
import com.example.sportsquiz.R
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentDependencies
import dagger.*
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
    override val questionsInfoDAO: QuestionsInfoDAO
    override val resources: Resources
    override val sharedPreferences: SharedPreferences


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun resources(resources: Resources): Builder

        fun build(): AppComponent
    }
}

@Module
class AppModule {

    @Provides
    fun provideNavHostsInfo(): NavHostsInfo = NavHostsInfo(
        globalNavHostId = R.id.global_fragment_container_view
    )

    @Provides
    fun provideQuestionsInfoDB(
        context: Context,
        resources: Resources
    ): QuestionsInfoDAO {
        val db = Room.databaseBuilder(
            context,
            QuestionsInfoDatabase::class.java,
            resources.getString(com.example.common.R.string.sports_quiz_database)
        ).build()
        return db.questionsInfoDAO()
    }

    @Provides
    fun provideSharedPreferences(
        context: Context,
        resources: Resources
    ): SharedPreferences =
        context.getSharedPreferences(
            resources.getString(com.example.common.R.string.sports_quiz_preferences),
            Context.MODE_PRIVATE
        )
}

@Module
interface AppModuleBinds {

    @Binds
    fun bindQuestionsInfoStorageImplToQuestionsInfoStorage(
        questionsInfoStorageImpl: QuestionsInfoDBStorageImpl
    ): QuestionsInfoDBStorage
}

@Scope
annotation class AppScope