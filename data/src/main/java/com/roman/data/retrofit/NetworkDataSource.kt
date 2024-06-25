package com.roman.data.retrofit

import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Singleton


class NetworkDataSource(
    retrofit: Retrofit
) {

    val musicFlay: GetMusicFly = retrofit.create(GetMusicFly::class.java)
    val recommendationsTicket: GetRecommendationsTicket =
        retrofit.create(GetRecommendationsTicket::class.java)
    val ticketInformFull: GetFullTicketsInform = retrofit.create(GetFullTicketsInform::class.java)
}

interface GetMusicFly {
    @GET("v3/ad9a46ba-276c-4a81-88a6-c068e51cce3a")
    suspend fun getMusicFly(): MusicFlayDto
}

interface GetRecommendationsTicket {
    @GET("v3/38b5205d-1a3d-4c2f-9d77-2f9d1ef01a4a")
    suspend fun getRecommendationsTicket(): TicketsRecommendationsListDto
}

interface GetFullTicketsInform {
    @GET("v3/c0464573-5a13-45c9-89f8-717436748b69")
    suspend fun getFullTicketsInform(): TicketsDto
}