package com.roman.mainsearch.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.entity.MusicFlay
import com.roman.core.LoadingState
import com.roman.domain.musicFly.GetMusicFlyUseCase
import com.roman.domain.musicFly.sharedPref.GetSharedPreferenceUseCase
import com.roman.domain.musicFly.sharedPref.SetSharedPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSearchViewModel @Inject constructor(
    private val useCaseMusicFly: GetMusicFlyUseCase,
    private val setSharedPreferenceUseCase: SetSharedPreferenceUseCase,
    private val getSharedPreferenceUseCase: GetSharedPreferenceUseCase
) : ViewModel() {

    private var _musicFlay: MusicFlay? = null
    val musicFlay get() = _musicFlay!!
    private var _inputFrom = ""
    private var _inputTo = ""
    val inputFrom get() = _inputFrom
    val inputTo get() = _inputTo
    private val _stateLoading = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val stateLoading = _stateLoading.asStateFlow()

    init {
        viewModelScope.launch {
            getMusicFlay()
        }
    }

    fun setFrom(textFrom: String) {
        _inputFrom = textFrom

    }

    fun setTo(textTo: String) {
        _inputTo = textTo
    }

    private suspend fun getMusicFlay() {
        try {
            _stateLoading.value = LoadingState.Loading
            _musicFlay = useCaseMusicFly.execute()
            _stateLoading.value = LoadingState.Loaded
        } catch (e: Exception) {
            _stateLoading.value = LoadingState.Error(e)
        }
    }

    fun getSharedPreference(): String {
        return getSharedPreferenceUseCase.execute()
    }

    fun setSharedPreference(textFromRoute: String) {
        setSharedPreferenceUseCase.execute(textFromRoute)
    }
}