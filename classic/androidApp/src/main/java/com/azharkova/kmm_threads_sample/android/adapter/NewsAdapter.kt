package com.azharkova.kn_network_sample.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azharkova.kn_network_sample.data.NewsItem


class NewsAdapter: RecyclerView.Adapter<NewsItemHolder>() {
    var items:ArrayList<NewsItem> = arrayListOf()

    fun update(data: List<NewsItem>){
        items = arrayListOf()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemHolder {
        return NewsItemHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NewsItemHolder, position: Int) {
        items[position].let {
            holder.tag = position
            holder.bindItem(it)
        }
    }
}