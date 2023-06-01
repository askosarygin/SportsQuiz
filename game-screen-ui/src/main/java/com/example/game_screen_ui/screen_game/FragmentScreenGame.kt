package com.example.game_screen_ui.screen_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.*
import com.example.game_screen_ui.R
import com.example.game_screen_ui.databinding.FragmentScreenGameBinding
import com.example.game_screen_ui.di.GameScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenGame : SportsQuizFragment(R.layout.fragment_screen_game) {
    private lateinit var binding: FragmentScreenGameBinding

    @Inject
    lateinit var factory: ViewModelScreenGame.Factory

    @Inject
    lateinit var navHostsInfo: NavHostsInfo

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

    override fun onResume() {
        super.onResume()

        val difficultLevel = getBundleNavigation(resources.getString(com.example.common.R.string.bundle_key_difficult)) as DifficultLevel

        when (difficultLevel.difficultLevel) {
            Difficult.Easy.name -> viewModel.loadEasyQuestionsInfo()
            Difficult.Normal.name -> viewModel.loadNormalQuestionsInfo()
            Difficult.Hard.name -> viewModel.loadHardQuestionsInfo()
        }
    }

    private fun initListeners() {
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
                        ViewModelScreenGame.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenDifficultySelection ->
                            navigateToActionId(
                                R.id.action_fragmentScreenGame_to_fragmentScreenDifficultySelection
                            )
                    }
                }
            }

            if (oldModel?.gameStateEvent != newModel.gameStateEvent) {
                newModel.gameStateEvent?.use { gameState ->
                    when (gameState) {
                        ViewModelScreenGame.Model.GameStateSingleLifeEvent.GameState.Stop ->
                            SportsQuizTwoButtonsDialogFragment(
                                manager = parentFragmentManager,
                                title = com.example.common.R.string.time_is_over,
                                message = "${
                                    resources.getString(
                                        com.example.common.R.string.game_result_first_part
                                    )
                                } ${
                                    newModel.earnedPoints
                                } ${
                                    resources.getString(
                                        com.example.common.R.string.game_result_second_part
                                    )
                                } ${newModel.earnedPoints} ${
                                    resources.getString(
                                        com.example.common.R.string.game_result_third_part
                                    )
                                }",
                                positiveButtonText = com.example.common.R.string.main_screen,
                                positiveButtonAction = { _, _ ->
                                    navigateToModule(
                                        ModuleNames.MainScreen,
                                        navHostsInfo.globalNavHostId
                                    )
                                },
                                negativeButtonText = com.example.common.R.string.play_again,
                                negativeButtonAction = { _, _ ->
                                    navigateToActionId(
                                        R.id.action_fragmentScreenGame_to_fragmentScreenDifficultySelection
                                    )
                                }
                            ).showDialog()
                    }
                }
            }

            if (oldModel?.buttonNextQuestionVisible != newModel.buttonNextQuestionVisible) {
                if (newModel.buttonNextQuestionVisible) {
                    binding.btnNextQuestion.visibility = View.VISIBLE
                } else {
                    binding.btnNextQuestion.visibility = View.GONE
                }

            }

            if (oldModel?.earnedPoints != newModel.earnedPoints) {
                binding.tvPointsCount.text = newModel.earnedPoints.toString()
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