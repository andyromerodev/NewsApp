package com.example.newsapp

import com.example.newsapp.provider.NewsProvider
import com.example.newsapp.repository.InvalidApiKeyException
import com.example.newsapp.repository.MissingApiKeyException
import com.example.newsapp.repository.NewsRepositoryImp
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets


class NewsRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val newsProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsProvider::class.java)

    private val newsRepository = NewsRepositoryImp(newsProvider)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //Running the test
    @Test
    fun `Top Headlines response is correct`() {
        mockWebServer.enqueueResponse("top_headlines.json")

        runBlocking {
            val articles = newsRepository.getNews("CU")
            assertEquals(2, articles.size)
            assertEquals("Ediciones Cinco Días", articles[0].author)
            assertEquals("LOS40", articles[1].author)

        }
    }

    @Test
    fun `Api key missing exception` (){
        mockWebServer.enqueueResponse("api_key_missing.json")
        assertThrows(MissingApiKeyException::class.java){
            runBlocking {
                newsRepository.getNews("CU")
            }
        }
    }

    @Test
    fun `Api key invalid exception` (){
        mockWebServer.enqueueResponse("api_key_invalid.json")
        assertThrows(InvalidApiKeyException::class.java){
            runBlocking {
                newsRepository.getNews("CU")
            }
        }
    }
    fun MockWebServer.enqueueResponse(filePath: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
        val source = inputStream?.source()?.buffer()
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(it.readString(StandardCharsets.UTF_8))
            )
        }
    }
}