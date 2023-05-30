package com.example.game_screen_ui.di

import androidx.lifecycle.ViewModel
import com.example.common.NavHostsInfo
import com.example.data.db.QuestionsInfoDAO
import com.example.game_screen_ui.screen_difficulty_selection.FragmentScreenDifficultySelection
import com.example.game_screen_ui.screen_game.FragmentScreenGame
import dagger.Component
import dagger.Module
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
class GameScreenModule

@Module
interface GameScreenModuleBinds {

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

interface GameScreenComponentDependencies {
    val navHostsInfo: NavHostsInfo
    val questionsInfoDAO: QuestionsInfoDAO
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