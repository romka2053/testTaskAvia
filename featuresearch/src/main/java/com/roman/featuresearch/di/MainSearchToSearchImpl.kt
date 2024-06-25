package com.roman.featuresearch.di

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.roman.featuresearch.R
import com.roman.mainsearch.di.MainSearchToSearch
import com.roman.search.SearchFragment.Companion.FROM_TEXT
import com.roman.search.SearchFragment.Companion.TO_TEXT

class MainSearchToSearchImpl:MainSearchToSearch{
    override fun navigate(nav: NavController, from: String, to: String) {
        val bundle=Bundle().apply {
            putString(FROM_TEXT,from)
            putString(TO_TEXT,to)
        }
       nav.navigate(
           args = bundle,
           resId = R.id.action_mainSearchFragment_to_searchFragment)

    }
}