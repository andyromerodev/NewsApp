package com.example.newsapp.repository

import com.example.newsapp.model.News
import com.example.newsapp.provider.NewsProvider
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(country: String): List<News>
    fun getNew(title: String): News
}

class NewsRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider // Injecting NewsProvider
): NewsRepository {

    private var news: List<News> = emptyList()

    override suspend fun getNews(country: String): List<News> {
        val apiResponse = newsProvider.topHeadLines(country).body() // Querying to the endpoint
        if (apiResponse?.status == "error"){// checking status
            when(apiResponse.code){// checking apiKey
                "apiKeyMissing" -> throw MissingApiKeyException()
                "apiKeyInvalid" -> throw InvalidApiKeyException()
                else -> throw Exception()
            }
        }

        news = apiResponse?.articles?: emptyList()
        return news

    }

    override fun getNew(title: String): News =
        news.first{it.title == title}

}

class MissingApiKeyException: java.lang.Exception()
class InvalidApiKeyException: java.lang.Exception()
