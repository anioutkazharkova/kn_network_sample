package com.azharkova.newsapp.service

import com.azharkova.kmm_concurrency_sample.HttpClient
import com.azharkova.kmm_concurrency_sample.Method
import com.azharkova.kmm_concurrency_sample.Request
import com.azharkova.news.data.NewsItem
import com.azharkova.news.data.NewsList

class NewsService constructor(val networkClient: HttpClient) {

    suspend fun getNewsList():ContentResponse<NewsList>{
        val response = networkClient.request(Request(url = NEWS_LIST, method = Method.GET, headers = hashMapOf("X-Api-Key" to API_KEY,
        "Content-Type" to "application/json", "Accept" to "application/json")))


       val news: ContentResponse<NewsList> = JsonDecoder.instance.decode(response.content.orEmpty())
        return news
    }

    companion object Urls {
        const val NEWS_LIST = "https://newsapi.org/v2/top-headlines?language=en"
        const val API_URL = "newsapi.org"
        const val API_KEY= "5b86b7593caa4f009fea285cc74129e2"
    }
}