package com.example.newsapp.di

import com.example.newsapp.provider.NewsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

//Preparing the dependency injection with Dagger Hilt

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://newsapi.org/v2".toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).build()
    }

    @Singleton
    @Provides
    fun providerNewsProvider(retrofit: Retrofit): NewsProvider =
        retrofit.create(NewsProvider::class.java)
}