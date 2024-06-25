package com.roman.domain.musicFly.sharedPref

import com.roman.domain.musicFly.sharedPref.di.GetSharedPreferenceRepository
import javax.inject.Inject

class GetSharedPreferenceUseCase @Inject constructor(
    private val getSharedPreferenceRepository: GetSharedPreferenceRepository
) {
    fun execute():String{
        return getSharedPreferenceRepository.getSharedPref()
    }

}