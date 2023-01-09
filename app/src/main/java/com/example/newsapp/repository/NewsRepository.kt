package com.example.newsapp.repository

import com.example.newsapp.model.News

interface NewsRepository {
    suspend fun getNews(country: String): List<News>
    fun getNew(title: String): News
}

class NewsRepositoryImp: NewsRepository {
    override suspend fun getNews(country: String): List<News> {
        TODO("Not yet implemented")
    }

    override fun getNew(title: String): News {
        TODO("Not yet implemented")
    }
}
