package com.azharkova.kn_network_sample


import com.azharkova.kmm_network.HttpClient
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