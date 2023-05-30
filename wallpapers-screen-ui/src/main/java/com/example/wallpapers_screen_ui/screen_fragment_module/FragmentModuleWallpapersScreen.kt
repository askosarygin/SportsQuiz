package com.example.wallpapers_screen_ui.screen_fragment_module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.common.SportsQuizFragment
import com.example.wallpapers_screen_ui.R
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentViewModel

class FragmentModuleWallpapersScreen : SportsQuizFragment(R.layout.fragment_module_wallpapers_screen) {
    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<WallpapersScreenComponentViewModel>()

        super.onAttach(context)
    }
}