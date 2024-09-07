package com.example.mycomposedemo.repo.net

import com.example.mycomposedemo.repo.api.PearnoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PearnoRetrofitInstance {
    private const val BASE_URL = "https://api.pearktrue.cn/api/"

    val api : PearnoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PearnoApiService::class.java)
    }
}
