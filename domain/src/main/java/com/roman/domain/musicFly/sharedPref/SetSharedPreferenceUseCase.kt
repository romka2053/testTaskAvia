package com.roman.domain.musicFly.sharedPref

import com.roman.domain.musicFly.sharedPref.di.SetSharePreferenceRepository
import javax.inject.Inject

class SetSharedPreferenceUseCase @Inject constructor(
    private val setSharePreferenceRepository: SetSharePreferenceRepository
) {
    fun execute(textFrom:String){
        return setSharePreferenceRepository.setSharedPreference(textFrom)
    }
}