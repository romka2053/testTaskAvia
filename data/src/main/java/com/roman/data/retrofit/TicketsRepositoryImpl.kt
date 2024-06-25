package com.roman.data.retrofit

import com.roman.entity.MusicFlay
import com.roman.entity.Tickets
import com.roman.entity.TicketsRecommendationsList
import com.roman.domain.allTickets.AllTicketsRepository
import com.roman.domain.musicFly.MusicFlyRepository
import com.roman.domain.ticketRecommendations.RecommendationsRepository
import javax.inject.Inject

class TicketsRepositoryImpl @Inject constructor(
    private val netWorkDataSource: NetworkDataSource
) : MusicFlyRepository, RecommendationsRepository, AllTicketsRepository {


    override suspend fun getMusicFly(): MusicFlay {
        return netWorkDataSource.musicFlay.getMusicFly()
    }

    override suspend fun getRecommendations(): TicketsRecommendationsList {
        return netWorkDataSource.recommendationsTicket.getRecommendationsTicket()
    }

    override suspend fun getTicketInformFull(): Tickets {
        return netWorkDataSource.ticketInformFull.getFullTicketsInform()
    }
}