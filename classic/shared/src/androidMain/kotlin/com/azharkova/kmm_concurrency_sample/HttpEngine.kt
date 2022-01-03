package com.azharkova.kmm_concurrency_sample

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class HttpEngine {
    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    suspend fun request(requestData: com.azharkova.kmm_concurrency_sample.Request):com.azharkova.kmm_concurrency_sample.Response {
        var requestBuilder = Request.Builder()
            .url(requestData.url)

        for (header in requestData.headers) {
            requestBuilder.addHeader(header.key.toString(), header.value)
        }
        requestBuilder.method(requestData.method.value,null)

        val request = requestBuilder.build()
        try {
            val response = client.newCall(request).await()

            return Response(code = response.code.toLong(), content = response.body?.string().orEmpty())
        } catch (e: Exception) {
            return Response(error = Error(e.message))
        }
    }
}