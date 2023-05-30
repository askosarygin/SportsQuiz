package com.example.main_screen_ui.screen_fragment_module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.common.SportsQuizFragment
import com.example.main_screen_ui.R
import com.example.main_screen_ui.di.MainScreenComponentViewModel

class FragmentModuleMainScreen : SportsQuizFragment(R.layout.fragment_module_main_screen) {
    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<MainScreenComponentViewModel>()

        super.onAttach(context)
    }
}