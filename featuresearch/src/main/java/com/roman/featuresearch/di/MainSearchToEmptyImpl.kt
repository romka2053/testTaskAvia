package com.roman.featuresearch.di

import androidx.navigation.NavController
import com.roman.featuresearch.R
import com.roman.mainsearch.di.MainSearchToEmpty

class MainSearchToEmptyImpl: MainSearchToEmpty {
    override fun navigate(nav: NavController) {
        nav.navigate(R.id.action_mainSearchFragment_to_blankFragment)
    }
}