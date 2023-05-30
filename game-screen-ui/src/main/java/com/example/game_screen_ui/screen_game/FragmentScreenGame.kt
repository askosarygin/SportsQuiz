package com.example.game_screen_ui.screen_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.SportsQuizFragment
import com.example.game_screen_ui.R
import com.example.game_screen_ui.databinding.FragmentScreenGameBinding
import com.example.game_screen_ui.di.GameScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenGame : SportsQuizFragment(R.layout.fragment_screen_game) {
    private lateinit var binding: FragmentScreenGameBinding

    @Inject
    lateinit var factory: ViewModelScreenGame.Factory

    private val viewModel by viewModels<ViewModelScreenGame> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenGameBinding.inflate(
            inflater,
            container,
            false
        )

        GameScreenComponentViewModel.getComponent().inject(this)

        initCollect()

        initListeners()

        return binding.root
    }

    private fun initListeners() {

    }

    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenGame.Model.NavigationEvent.NavigationDestination.ScreenResults ->
                            TODO()
                    }
                }
            }
        }
    }
}