package com.azharkova.newsapp

import com.azharkova.kmm_concurrency_sample.HttpClient
import com.azharkova.newsapp.service.NewsService
import kotlin.native.concurrent.ThreadLocal

class DI {
    @ThreadLocal
    companion object DI {
        val instance = DI()
    }

    val networkClient: HttpClient by lazy {
        HttpClient()
    }

    val newsService: NewsService by lazy {
        NewsService(networkClient)
    }
}