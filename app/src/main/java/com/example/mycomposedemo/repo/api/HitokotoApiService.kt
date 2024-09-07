package com.example.mycomposedemo.repo.api

import com.example.mycomposedemo.repo.models.HitokotoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HitokotoApiService {

    @GET("/")
    suspend fun getHitokoto(@Query("c") c : String? = null): HitokotoModel
}