package com.roman.core

sealed class LoadingState {
    data object Loading:LoadingState()
    data object Loaded:LoadingState()
    data class Error(val description: Exception):LoadingState()
}