package com.roman.search.di

import androidx.navigation.NavController
import com.roman.core.ParcelableSearchSetting

interface SearchToAllTickets {
    fun navigate(nav:NavController,parcelableSetting: ParcelableSearchSetting)
}