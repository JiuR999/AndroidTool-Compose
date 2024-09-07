package com.example.mycomposedemo.repo.net

import com.example.mycomposedemo.repo.api.PearnoApiService
import com.example.mycomposedemo.repo.api.PictureApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PictureRetrofitInstance {
    private const val BASE_URL = "https://service.picasso.adesk.com/v1/"

    val api : PictureApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PictureApiService::class.java)
    }
}
