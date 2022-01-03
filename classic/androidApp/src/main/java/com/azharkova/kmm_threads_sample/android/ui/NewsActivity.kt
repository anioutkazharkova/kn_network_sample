package com.azharkova.kn_network_sample.android.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azharkova.kn_network_sample.NewsViewModel
import com.azharkova.kn_network_sample.android.R
import com.azharkova.kn_network_sample.android.adapter.NewsAdapter
import com.azharkova.kn_network_sample.data.NewsItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity(){
    private var listView: RecyclerView? = null
    private val viewModel: NewsViewModel by lazy {
        NewsViewModel()
    }
    private var adapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        listView = findViewById<RecyclerView>(R.id.news_list)
        listView?.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsFlow.collect {
                    setupNews(it?.articles.orEmpty())
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    fun setupNews(list: List<NewsItem>) {
        if (adapter == null){
            adapter = NewsAdapter()
        }
        listView?.adapter = adapter
        adapter?.update(list)
    }

}
