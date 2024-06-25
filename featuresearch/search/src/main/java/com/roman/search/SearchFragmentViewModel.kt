package com.roman.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.entity.TicketRecommendations

import com.roman.core.LoadingState
import com.roman.domain.ticketRecommendations.RecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val recommendationsUseCase: RecommendationsUseCase
) : ViewModel() {
    private val formatDate = SimpleDateFormat("d MMMM", Locale.getDefault())
    private var _ticketRecommendations: List<TicketRecommendations>? = null
    val ticketsRecommendationsList get() = _ticketRecommendations!!
    private val _stateLoading = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val stateLoading get()=_stateLoading.asStateFlow()
    private var _dateFrom = 0L
    val dateFrom get() = _dateFrom
    private var _dateTo = 0L
    val dateTo get() = _dateTo
    private var _routFrom = ""
    private var _routTo = ""
    val routFrom get() = _routFrom
    val routTo get() = _routTo

    init {
        viewModelScope.launch {
            try {
                _stateLoading.value=LoadingState.Loading
                _ticketRecommendations = recommendationsUseCase.getRecommendations().ticketsOffers
                _stateLoading.value=LoadingState.Loaded
            }catch (e:Exception)
            {
                _stateLoading.value=LoadingState.Error(e)
            }

        }

    }

    fun setRoutFrom(text:String){
        _routFrom=text
    }
    fun setRouteTo(text:String){
        _routTo=text
    }
    fun reverse(textFrom:String,textTo:String){

        _routFrom = textTo
        _routTo = textFrom
    }
    fun clearTo(){
        _routTo=""
    }

    fun setDateFrom(millSec: Long) {
        _dateFrom = millSec
    }

    fun setDateTo(millSec: Long) {
        _dateTo = millSec
    }

    fun getFromDayAndMouth(): String {

        return formatDate.format(_dateFrom).toString()
    }
    fun getToDayAndMouth(): String {
        return if (_dateTo != 0L) formatDate.format(_dateTo).toString()
        else ""
    }
}