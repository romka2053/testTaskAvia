package com.roman.domain.ticketRecommendations

import com.roman.entity.TicketsRecommendationsList
import javax.inject.Inject

class RecommendationsUseCase @Inject constructor(
    private val recommendations: RecommendationsRepository
) {

    suspend fun getRecommendations(): TicketsRecommendationsList {
        return recommendations.getRecommendations()
    }
}