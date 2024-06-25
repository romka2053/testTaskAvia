package com.roman.featuresearch.di

import com.roman.mainsearch.di.MainSearchToEmpty
import com.roman.mainsearch.di.MainSearchToSearch
import com.roman.search.di.SearchToAllTickets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(FragmentComponent::class)
object NavigationSearchModule {

    @Provides
    fun mainSearchToSearch():MainSearchToSearch{
        return MainSearchToSearchImpl()
    }

    @Provides
    fun searchToAllTickets(): SearchToAllTickets {
        return SearchToAllTicketsImpl()
    }
    @Provides
    fun searchToEmpty(): MainSearchToEmpty {
        return MainSearchToEmptyImpl()
    }


}