package com.example.game_screen_ui.screen_difficulty_selection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.SportsQuizFragment
import com.example.game_screen_ui.R
import com.example.game_screen_ui.databinding.FragmentScreenDifficultySelectionBinding
import com.example.game_screen_ui.di.GameScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenDifficultySelection : SportsQuizFragment(R.layout.fragment_screen_difficulty_selection) {
    private lateinit var binding: FragmentScreenDifficultySelectionBinding

    @Inject
    lateinit var factory: ViewModelScreenDifficultySelection.Factory

    private val viewModel by viewModels<ViewModelScreenDifficultySelection> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenDifficultySelectionBinding.inflate(
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
        binding.btnEasy.setOnClickListener {
            viewModel.buttonEasyPressed()
        }

        binding.btnNormal.setOnClickListener {
            viewModel.buttonNormalPressed()
        }

        binding.btnHard.setOnClickListener {
            viewModel.buttonHardPressed()
        }
    }

    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenDifficultySelection.Model.NavigationEvent.NavigationDestination.ScreenGameEasy ->
                            findNavController().navigate(
                                R.id.action_fragmentScreenDifficultySelection_to_fragmentScreenGame
                            )
                        ViewModelScreenDifficultySelection.Model.NavigationEvent.NavigationDestination.ScreenGameNormal ->
                            Log.i("MY_TAG", "Переход на экран игры с передачей параметров НОРМАЛЬНОЙ сложности")
                        ViewModelScreenDifficultySelection.Model.NavigationEvent.NavigationDestination.ScreenGameHard ->
                            Log.i("MY_TAG", "Переход на экран игры с передачей параметров ТЯЖЁЛОЙ сложности")
                    }
                }
            }
        }
    }
}