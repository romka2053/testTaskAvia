package com.roman.data.sharedpreference

import android.content.SharedPreferences
import com.roman.domain.musicFly.sharedPref.di.GetSharedPreferenceRepository
import com.roman.domain.musicFly.sharedPref.di.SetSharePreferenceRepository
import javax.inject.Inject

class SharedPreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : GetSharedPreferenceRepository, SetSharePreferenceRepository {

    override fun getSharedPref(): String {
        return sharedPreferences.getString(SHARE_PREFERENCE_STRING, "")!!
    }

    override fun setSharedPreference(text: String) {
        val editor = sharedPreferences.edit()
        editor.putString(SHARE_PREFERENCE_STRING, text).apply()
    }

    private companion object {
        const val SHARE_PREFERENCE_STRING = "shared_preference_string"
    }
}