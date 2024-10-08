package com.example.mycomposedemo.repo.net

import com.example.mycomposedemo.repo.api.HitokotoApiService
import com.example.mycomposedemo.repo.api.PearnoApiService
import com.example.mycomposedemo.repo.models.HitokotoModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HitokotoRetrofitInstance {
    private const val BASE_URL = "https://v1.hitokoto.cn/"
    val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HitokotoApiService::class.java)
    }
}