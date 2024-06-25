package com.roman.featuresearch.di

import android.os.Bundle
import androidx.navigation.NavController
import com.roman.alltickets.AllTicketsFragment
import com.roman.core.ParcelableSearchSetting
import com.roman.featuresearch.R
import com.roman.search.SearchFragment
import com.roman.search.di.SearchToAllTickets

class SearchToAllTicketsImpl:SearchToAllTickets {
    override fun navigate(nav:NavController,parcelableSetting: ParcelableSearchSetting) {
        val bundle=Bundle().apply {
            putParcelable(AllTicketsFragment.RUT_PARCELABLE,parcelableSetting)
        }
        nav.navigate(
            resId = R.id.action_searchFragment_to_allTicketsFragment,
            args = bundle
            )
    }
}