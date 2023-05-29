package com.example.common

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class SportsQuizFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    protected fun <MODEL> Flow<MODEL>.collectWithOld(
        scope: CoroutineScope,
        action: suspend (oldModel: MODEL?, newModel: MODEL) -> Unit
    ) {
        var oldModel: MODEL? = null
        onEach { newModel ->
            action(oldModel, newModel)
            oldModel = newModel
        }.launchIn(scope)
    }
}