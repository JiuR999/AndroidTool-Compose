package com.example.mycomposedemo.repo.api

import com.example.mycomposedemo.repo.models.wallerpaper.WallerCategory
import com.example.mycomposedemo.repo.models.wallerpaper.WallerDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PictureApiService {
    @GET("lightwp/category")
    suspend fun getLightwpCategory(): WallerCategory
    // https://service.picasso.adesk.com/v1/vertical/category/4e4d610cdf714d2966000003/vertical
    @GET("vertical/category/{id}/vertical")
    suspend fun getWallerDetail(@Path("id") id: String): WallerDetail
}
