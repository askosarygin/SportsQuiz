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
        binding.tvPointsText.setOnClickListener { //todo тест запуска
            viewModel.loadEasyQuestionsInfo()
        }

        binding.tvAnswerOne.setOnClickListener {
            viewModel.answerOneToggleSelection()
        }
        binding.tvAnswerTwo.setOnClickListener {
            viewModel.answerTwoToggleSelection()
        }
        binding.tvAnswerThree.setOnClickListener {
            viewModel.answerThreeToggleSelection()
        }
        binding.tvAnswerFour.setOnClickListener {
            viewModel.answerFourToggleSelection()
        }
        binding.btnNextQuestion.setOnClickListener {
            viewModel.buttonNextQuestionPressed()
        }
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

            if (oldModel?.timer != newModel.timer) {
                binding.tvTimer.text = newModel.timer
            }

            if (oldModel?.question != newModel.question) {
                binding.tvQuestion.text = newModel.question
            }
            if (oldModel?.answerOne != newModel.answerOne) {
                binding.tvAnswerOne.text = newModel.answerOne
            }
            if (oldModel?.answerTwo != newModel.answerTwo) {
                binding.tvAnswerTwo.text = newModel.answerTwo
            }
            if (oldModel?.answerThree != newModel.answerThree) {
                binding.tvAnswerThree.text = newModel.answerThree
            }
            if (oldModel?.answerFour != newModel.answerFour) {
                binding.tvAnswerFour.text = newModel.answerFour
            }

            if (oldModel?.answerOneSelected != newModel.answerOneSelected) {
                if (newModel.answerOneSelected) {
                    binding.tvAnswerOne.setBackgroundResource(com.example.common.R.color.sports_quiz_blue)
                } else {
                    binding.tvAnswerOne.setBackgroundResource(com.example.common.R.color.sports_quiz_white)
                }
            }
            if (oldModel?.answerTwoSelected != newModel.answerTwoSelected) {
                if (newModel.answerTwoSelected) {
                    binding.tvAnswerTwo.setBackgroundResource(com.example.common.R.color.sports_quiz_blue)
                } else {
                    binding.tvAnswerTwo.setBackgroundResource(com.example.common.R.color.sports_quiz_white)
                }
            }
            if (oldModel?.answerThreeSelected != newModel.answerThreeSelected) {
                if (newModel.answerThreeSelected) {
                    binding.tvAnswerThree.setBackgroundResource(com.example.common.R.color.sports_quiz_blue)
                } else {
                    binding.tvAnswerThree.setBackgroundResource(com.example.common.R.color.sports_quiz_white)
                }
            }
            if (oldModel?.answerFourSelected != newModel.answerFourSelected) {
                if (newModel.answerFourSelected) {
                    binding.tvAnswerFour.setBackgroundResource(com.example.common.R.color.sports_quiz_blue)
                } else {
                    binding.tvAnswerFour.setBackgroundResource(com.example.common.R.color.sports_quiz_white)
                }
            }
        }
    }

}