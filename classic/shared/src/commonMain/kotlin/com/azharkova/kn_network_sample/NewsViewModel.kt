package com.azharkova.kn_network_sample

import com.azharkova.kn_network_sample.threads.uiDispatcher
import com.azharkova.kn_network_sample.data.NewsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel{
    val job = SupervisorJob()
    val scope = CoroutineScope(uiDispatcher + job)

    val newsFlow = MutableStateFlow<NewsList?>(null)
    val flowNewsItem =  newsFlow.wrapToAny()

    val newsService: NewsService by lazy {
        DI.instance.newsService
    }

    fun loadData() {
        scope.launch {
           val result =  newsService.getNewsList()
            newsFlow.value = result.content
        }
    }
}