package com.example.newsapp.provider

import com.example.newsapp.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "c69f5ed123674b249cf024f203ebe5ee"

interface NewsProvider {
    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun topHeadLines(@Query("country") country: String): Response<NewsApiResponse>
}