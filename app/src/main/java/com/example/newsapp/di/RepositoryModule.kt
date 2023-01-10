package com.example.newsapp.di

import com.example.newsapp.provider.NewsProvider
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.NewsRepositoryImp
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

    @Singleton
    @Provides
    fun providerNewsProvider(provider: NewsProvider): NewsRepository =
        NewsRepositoryImp(provider)
}