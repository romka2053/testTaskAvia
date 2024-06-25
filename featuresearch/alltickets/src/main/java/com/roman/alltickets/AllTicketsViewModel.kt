package com.roman.alltickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.entity.Tickets
import com.roman.core.LoadingState
import com.roman.domain.allTickets.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTicketsViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {
    private val _stateLoading = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val stateLoading get() = _stateLoading
    private var _tickets: Tickets?=null
    val tickets get() = _tickets!!

    init {
        viewModelScope.launch {
            getTickets()
        }

    }
    private suspend fun getTickets(){
        try {
            _stateLoading.value=LoadingState.Loading
            _tickets=getTicketsUseCase.execute()
            _stateLoading.value=LoadingState.Loaded
        }
        catch (e:Exception){
            _stateLoading.value=LoadingState.Error(e)
        }

    }


}