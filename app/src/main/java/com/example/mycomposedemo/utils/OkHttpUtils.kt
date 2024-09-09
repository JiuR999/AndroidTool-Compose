package com.example.mycomposedemo.utils

import okhttp3.OkHttpClient
import okhttp3.Request

object OkHttpUtils {

    lateinit var client : OkHttpClient

    init {
        client = OkHttpClient.Builder().build()
    }

    suspend fun get(url: String): String {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return response.body?.string() ?: "error"
    }
}