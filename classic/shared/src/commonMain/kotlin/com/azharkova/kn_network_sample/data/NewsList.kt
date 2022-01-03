package com.azharkova.kn_network_sample.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsList(
    var articles:List<NewsItem>
)