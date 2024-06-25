package com.roman.data.retrofit

import com.roman.domain.allTickets.AllTicketsRepository
import com.roman.domain.musicFly.MusicFlyRepository
import com.roman.domain.ticketRecommendations.RecommendationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Named("network_url")
    fun providesBaseUrl(): String = "https://run.mocky.io/"

    @Provides
    @Singleton
    fun provideRetrofit(@Named("network_url") baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .build()


    @Provides
    @Singleton
    fun provideDataModule(retrofit: Retrofit): NetworkDataSource = NetworkDataSource(retrofit)

    @Provides
    fun providerGetMusicFly(networkDataSource: NetworkDataSource): MusicFlyRepository =
        TicketsRepositoryImpl(networkDataSource)

    @Provides
    fun providerRecommendations(networkDataSource: NetworkDataSource): RecommendationsRepository =
        TicketsRepositoryImpl(networkDataSource)

    @Provides
    fun providerTickets(networkDataSource: NetworkDataSource): AllTicketsRepository =
        TicketsRepositoryImpl(networkDataSource)


}