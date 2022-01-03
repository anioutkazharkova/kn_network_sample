package com.azharkova.kmm_threads_sample.android

import androidx.lifecycle.ViewModel
import com.azharkova.news.data.NewsItem
import com.azharkova.newsapp.DI
import com.azharkova.newsapp.service.NewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {
    private val service: NewsService by lazy {
        DI.instance.newsService
    }
    val newsList:MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun loadNews() {
        scope.launch {
           val news =  service.getNewsList()
           newsList.tryEmit(news.content?.articles.orEmpty())
        }
    }

}