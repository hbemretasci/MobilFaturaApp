package com.codmine.fatura.di

import com.codmine.fatura.repository.FaturaRepository
import com.codmine.fatura.service.FaturaAPI
import com.codmine.fatura.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMukellefRepository(
        api: FaturaAPI
    ) = FaturaRepository(api)

    @Singleton
    @Provides
    fun provideMukellefApi() : FaturaAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FaturaAPI::class.java)
    }

}