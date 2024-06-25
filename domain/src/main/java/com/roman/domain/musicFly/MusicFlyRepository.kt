package com.roman.domain.musicFly

import com.roman.entity.MusicFlay

interface MusicFlyRepository {
    suspend fun getMusicFly(): MusicFlay
}