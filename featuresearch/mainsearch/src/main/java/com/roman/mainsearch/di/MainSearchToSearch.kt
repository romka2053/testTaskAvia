package com.roman.mainsearch.di

import androidx.navigation.NavController

interface MainSearchToSearch {
    fun navigate(nav:NavController,from:String,to:String)
}