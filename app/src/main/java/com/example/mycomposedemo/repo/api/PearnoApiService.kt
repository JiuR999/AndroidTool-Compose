package com.example.mycomposedemo.repo.api

import com.example.mycomposedemo.models.History
import com.example.mycomposedemo.repo.models.pearno.BrainTeasersModel
import com.example.mycomposedemo.repo.models.pearno.HotNew
import com.example.mycomposedemo.repo.models.pearno.KFCText
import com.example.mycomposedemo.router.Screen
import retrofit2.http.GET
import retrofit2.http.Query

interface PearnoApiService {
    @GET("lsjt/?type=json")
    suspend fun getHistory(): History

    @GET("social/hotlist.php")
    suspend fun getHotNews(@Query("type") type: String): HotNew

    @GET("kfc")
    suspend fun getKFC(): KFCText

    @GET("brainteasers")
    suspend fun getBrainteasers(): BrainTeasersModel
}
