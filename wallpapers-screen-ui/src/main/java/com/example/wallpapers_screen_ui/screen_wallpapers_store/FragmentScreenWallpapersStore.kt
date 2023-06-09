package com.example.wallpapers_screen_ui.screen_wallpapers_store

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common.*
import com.example.wallpapers_screen_ui.R
import com.example.wallpapers_screen_ui.databinding.FragmentScreenWallpapersStoreBinding
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenWallpapersStore : SportsQuizFragment(R.layout.fragment_screen_wallpapers_store) {
    private lateinit var binding: FragmentScreenWallpapersStoreBinding

    @Inject
    lateinit var factory: ViewModelScreenWallpapersStore.Factory

    @Inject
    lateinit var navHostsInfo: NavHostsInfo

    private val viewModel by viewModels<ViewModelScreenWallpapersStore> {
        factory
    }

    private var wallpapers = arrayListOf<Wallpaper>()
    private val adapter = RecyclerViewAdapterScreenWallpapersStore(wallpapers) { wallpaperPosition ->
        viewModel.wallpaperSelected(wallpaperPosition)
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

        initWallpapers()

        return binding.root
    }

    override fun onResume() {
        viewModel.getPoints()

        super.onResume()
    }

    private fun initWallpapers() {
        binding.rvWallpapers.addItemDecoration(SpacingItemDecorator(24))
        binding.rvWallpapers.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvWallpapers.adapter = adapter
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.buttonBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenWallpapersStore.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenStart ->
                            navigateToModule(
                                ModuleNames.MainScreen,
                                navHostsInfo.globalNavHostId
                            )
                        ViewModelScreenWallpapersStore.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWallpaper ->
                            navigateToActionId(
                                R.id.action_fragmentScreenWallpapersStore_to_fragmentScreenWallpaper,
                                newModel.selectedWallpaper,
                                resources.getString(com.example.common.R.string.bundle_key_wallpaper)
                            )
                    }
                }
            }
            if (oldModel?.points != newModel.points) {
                binding.tvBalanceCount.text = newModel.points.toString()
            }
            if (oldModel?.wallpapers != newModel.wallpapers) {
                wallpapers.apply {
                    clear()
                    addAll(newModel.wallpapers)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}