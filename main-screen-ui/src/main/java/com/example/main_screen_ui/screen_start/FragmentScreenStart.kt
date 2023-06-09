package com.example.main_screen_ui.screen_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.ModuleNames
import com.example.common.NavHostsInfo
import com.example.common.SportsQuizFragment
import com.example.main_screen_ui.R
import com.example.main_screen_ui.databinding.FragmentScreenStartBinding
import com.example.main_screen_ui.di.MainScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenStart : SportsQuizFragment(R.layout.fragment_screen_start) {
    private lateinit var binding: FragmentScreenStartBinding

    @Inject
    lateinit var factory: ViewModelScreenStart.Factory

    @Inject
    lateinit var navHostsInfo: NavHostsInfo

    private val viewModel by viewModels<ViewModelScreenStart> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenStartBinding.inflate(
            inflater,
            container,
            false
        )

        MainScreenComponentViewModel.getComponent().inject(this)

        initCollect()

        initListeners()

        return binding.root
    }

    override fun onResume() {
        viewModel.getPoints()

        super.onResume()
    }

    private fun initListeners() {
        binding.btnNewGame.setOnClickListener {
            viewModel.buttonNewGamePressed()
        }

        binding.btnWallpapersStore.setOnClickListener {
            viewModel.buttonWallpapersStorePressed()
        }
    }

    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenStart.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenDifficultySelection ->
                            navigateToModule(
                                ModuleNames.GameScreen,
                                navHostsInfo.globalNavHostId
                            )
                        ViewModelScreenStart.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWallpapersStore ->
                            navigateToModule(
                                ModuleNames.WallpapersScreen,
                                navHostsInfo.globalNavHostId
                            )
                    }
                }
            }

            if (oldModel?.points != newModel.points) {
                binding.tvBalanceCount.text = newModel.points.toString()
            }
        }
    }
}