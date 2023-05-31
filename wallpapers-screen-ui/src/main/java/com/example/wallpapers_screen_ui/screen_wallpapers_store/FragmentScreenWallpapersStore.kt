package com.example.wallpapers_screen_ui.screen_wallpapers_store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.ModuleNames
import com.example.common.NavHostsInfo
import com.example.common.SportsQuizFragment
import com.example.wallpapers_screen_ui.R
import com.example.wallpapers_screen_ui.databinding.FragmentScreenWallpapersStoreBinding
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenWallpapersStore :
    SportsQuizFragment(R.layout.fragment_screen_wallpapers_store) {
    private lateinit var binding: FragmentScreenWallpapersStoreBinding

    @Inject
    lateinit var factory: ViewModelScreenWallpapersStore.Factory

    @Inject
    lateinit var navHostsInfo: NavHostsInfo

    private val viewModel by viewModels<ViewModelScreenWallpapersStore> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenWallpapersStoreBinding.inflate(
            inflater,
            container,
            false
        )

        WallpapersScreenComponentViewModel.getComponent().inject(this)

        initCollect()

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.buttonBackPressed()
        }
    }

    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenWallpapersStore.Model.NavigationEvent.NavigationDestination.ScreenStart ->
                            navigateToModule(
                                ModuleNames.MainScreen,
                                navHostsInfo.globalNavHostId
                            )
                    }
                }
            }
        }
    }
}