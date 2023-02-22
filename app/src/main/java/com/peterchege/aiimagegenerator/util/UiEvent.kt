package com.peterchege.aiimagegenerator.util

abstract class Event

sealed class UiEvent: Event() {
    data class ShowSnackbar(val uiText: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()


}