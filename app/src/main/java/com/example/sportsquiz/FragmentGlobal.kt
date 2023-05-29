package com.example.sportsquiz

import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.example.common.SportsQuizFragment

class FragmentGlobal : SportsQuizFragment(R.layout.fragment_global) {
    override fun onResume() {
        super.onResume()

        findNavController().navigate(Uri.parse("sports-quiz://main-screen"))
    }
}