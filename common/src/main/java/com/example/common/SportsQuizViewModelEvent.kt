package com.example.common

open class SportsQuizViewModelEvent<EVENT>(
    private val event: EVENT
) {

    fun use(doEvent: (EVENT) -> Unit) {
        doEvent(event)
    }
}