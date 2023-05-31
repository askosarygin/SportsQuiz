package com.example.game_screen_ui.di

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.data.RepositoryGameScreenDomainImpl
import com.example.data.db.QuestionsInfoDAO
import com.example.data.db.QuestionsInfoDBStorage
import com.example.data.db.QuestionsInfoDBStorageImpl
import com.example.game_screen_domain.Interactor
import com.example.game_screen_domain.InteractorImpl
import com.example.game_screen_domain.Repository
import com.example.game_screen_ui.screen_difficulty_selection.FragmentScreenDifficultySelection
import com.example.game_screen_ui.screen_game.FragmentScreenGame
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Scope
import kotlin.properties.Delegates

@[GameScreenScope Component(
    dependencies = [GameScreenComponentDependencies::class],
    modules = [
        GameScreenModule::class,
        GameScreenModuleBinds::class
    ]
)]
internal interface GameScreenComponent {

    fun inject(fragmentScreenDifficultySelection: FragmentScreenDifficultySelection)
    fun inject(fragmentScreenGame: FragmentScreenGame)

    @Component.Builder
    interface Builder {

        fun dependencies(gameScreenComponentDependencies: GameScreenComponentDependencies): Builder

        fun build(): GameScreenComponent
    }
}

@Module
class GameScreenModule {

    @Provides
    fun provideCoroutineScopeIO(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

}

@Module
interface GameScreenModuleBinds {

    @Binds
    fun bindRepositoryGameScreenDomainImplToRepository(
        repositoryGameScreenDomainImpl: RepositoryGameScreenDomainImpl
    ): Repository

    @Binds
    fun bindInteractorImplToInteractor(
        interactorImpl: InteractorImpl
    ): Interactor

    @Binds
    fun bindQuestionsInfoDBStorageImplToQuestionsInfoDBStorage(
        questionsInfoDBStorageImpl: QuestionsInfoDBStorageImpl
    ): QuestionsInfoDBStorage
}

interface GameScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
    val questionsInfoDAO: QuestionsInfoDAO
    val resources: Resources
    val sharedPreferences: SharedPreferences
}

object GameScreenComponentDependenciesStore {
    var dependencies: GameScreenComponentDependencies by Delegates.notNull()
}

internal class GameScreenComponentViewModel : ViewModel() {

    init {
        gameScreenComponent = createComponent()
    }

    companion object {
        private var gameScreenComponent: GameScreenComponent? = null

        private fun createComponent(): GameScreenComponent = gameScreenComponent
            ?: DaggerGameScreenComponent
                .builder()
                .dependencies(GameScreenComponentDependenciesStore.dependencies)
                .build()

        fun getComponent(): GameScreenComponent = gameScreenComponent
            ?: throw RuntimeException("GameScreen component is not initialized")

        private fun closeComponent() {
            gameScreenComponent = null
        }
    }

    override fun onCleared() {
        super.onCleared()

        closeComponent()
    }
}

@Scope
internal annotation class GameScreenScope