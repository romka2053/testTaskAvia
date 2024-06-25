package com.roman.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.roman.domain.musicFly.sharedPref.di.GetSharedPreferenceRepository
import com.roman.domain.musicFly.sharedPref.di.SetSharePreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("saved_data_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun getSharedPref(sharedPreferences: SharedPreferences): GetSharedPreferenceRepository {
        return SharedPreferenceRepositoryImpl(sharedPreferences)
    }

    @Provides
    fun setSharedPref(sharedPreferences: SharedPreferences): SetSharePreferenceRepository {
        return SharedPreferenceRepositoryImpl(sharedPreferences)
    }


}