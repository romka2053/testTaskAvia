package com.roman.domain.ticketRecommendations


import com.roman.entity.TicketsRecommendationsList

interface RecommendationsRepository {
    suspend fun getRecommendations(): TicketsRecommendationsList
}