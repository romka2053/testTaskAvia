package com.roman.core


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableSearchSetting(
    val routeFrom: String,
    val routeTo: String,
    val dateFrom: String,
    val dateTo: String,
    val countPeople: Int,
    ) : Parcelable