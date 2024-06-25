package com.roman.domain.musicFly

import com.roman.entity.MusicFlay
import javax.inject.Inject


class GetMusicFlyUseCase @Inject constructor(
    private val musicFlyRepository: MusicFlyRepository
) {
    suspend fun execute(): MusicFlay {
        return musicFlyRepository.getMusicFly()
    }
}