package com.example.wallpapers_screen_ui.screen_wallpaper

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.*
import com.example.wallpapers_screen_ui.R
import com.example.wallpapers_screen_ui.databinding.FragmentScreenWallpaperBinding
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentViewModel
import javax.inject.Inject

class FragmentScreenWallpaper : SportsQuizFragment(R.layout.fragment_screen_wallpaper) {
    private lateinit var binding: FragmentScreenWallpaperBinding

    @Inject
    lateinit var factory: ViewModelScreenWallpaper.Factory

    @Inject
    lateinit var navHostsInfo: NavHostsInfo

    private val viewModel by viewModels<ViewModelScreenWallpaper> {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenWallpaperBinding.inflate(
            inflater,
            container,
            false
        )

        WallpapersScreenComponentViewModel.getComponent().inject(this)

        initCollect()

        initListeners()

        val wallpaper =
            getBundleNavigation(resources.getString(com.example.common.R.string.bundle_key_wallpaper)) as Wallpaper
        viewModel.setWallpaperInfo(wallpaper)

        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun changeWallpaper(bitmap: Bitmap) {
        WallpaperManager.getInstance(requireContext()).setBitmap(bitmap)
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            navigateToActionId(R.id.action_fragmentScreenWallpaper_to_fragmentScreenWallpapersStore)
        }
    }

    private fun initCollect() {
        viewModel.model.collectWithOld(lifecycleScope) { oldModel, newModel ->
            if (oldModel?.navigationEvent != newModel.navigationEvent) {
                newModel.navigationEvent?.use { navigationDestination ->
                    when (navigationDestination) {
                        ViewModelScreenWallpaper.Model.NavigationSingleLifeEvent.NavigationDestination.ScreenWallpaperStore ->
                            navigateToActionId(R.id.action_fragmentScreenWallpaper_to_fragmentScreenWallpapersStore)
                    }
                }
            }
            if (oldModel?.buttonBuyVisible != newModel.buttonBuyVisible) {
                if (newModel.buttonBuyVisible) {
                    binding.btnBuyOrSetWallpaper.text =
                        resources.getText(com.example.common.R.string.buy)
                    binding.btnBuyOrSetWallpaper.setOnClickListener {
                        SportsQuizTwoButtonsDialogFragment(
                            manager = parentFragmentManager,
                            title = com.example.common.R.string.do_you_want_buy_this_wallpaper,
                            positiveButtonText = com.example.common.R.string.yes,
                            positiveButtonAction = { dialogInterface, _ ->
                                viewModel.buyWallpaper()
                                dialogInterface.cancel()
                            },
                            negativeButtonText = com.example.common.R.string.no,
                            negativeButtonAction = { dialogInterface, _ ->
                                dialogInterface.cancel()
                            }
                        ).showDialog()
                    }
                } else {
                    binding.btnBuyOrSetWallpaper.text =
                        resources.getText(com.example.common.R.string.set_wallpaper_to_phone)
                    binding.btnBuyOrSetWallpaper.setOnClickListener {
                        changeWallpaper(newModel.imageResource!!)
                        Toast.makeText(
                            requireContext(),
                            com.example.common.R.string.wallpaper_installed_on_desktop,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            if (oldModel?.name != newModel.name) {
                val wallpaperName =
                    "${resources.getString(com.example.common.R.string.wallpaper)} ${newModel.name}"
                binding.tvWallpaperName.text = wallpaperName
            }
            if (oldModel?.price != newModel.price) {
                if (newModel.price != -1) {
                    val wallpaperPrice =
                        "${newModel.price} ${resources.getString(com.example.common.R.string.points_price)}"
                    binding.tvWallpaperPrice.text = wallpaperPrice
                } else {
                    val wallpaperPriceBought =
                        resources.getString(com.example.common.R.string.bought)
                    binding.tvWallpaperPrice.text = wallpaperPriceBought
                }


            }
            if (oldModel?.imageResource != newModel.imageResource) {
                binding.ivWallpaper.setImageBitmap(newModel.imageResource)
            }
            if (oldModel?.points != newModel.points) {
                binding.tvBalanceCount.text = newModel.points.toString()
            }
            if (oldModel?.noPointsEvent != newModel.noPointsEvent) {
                SportsQuizOneButtonDialogFragment(
                    manager = parentFragmentManager,
                    message = resources.getString(com.example.common.R.string.no_enough_points),
                    positiveButtonText = com.example.common.R.string.ok
                ).showDialog()
            }
        }
    }
}