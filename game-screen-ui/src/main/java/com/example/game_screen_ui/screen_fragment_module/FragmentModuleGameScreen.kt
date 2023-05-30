package com.example.game_screen_ui.screen_fragment_module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.common.SportsQuizFragment
import com.example.game_screen_ui.R
import com.example.game_screen_ui.di.GameScreenComponentViewModel

class FragmentModuleGameScreen : SportsQuizFragment(R.layout.fragment_module_game_screen) {
    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<GameScreenComponentViewModel>()

        super.onAttach(context)
    }
}