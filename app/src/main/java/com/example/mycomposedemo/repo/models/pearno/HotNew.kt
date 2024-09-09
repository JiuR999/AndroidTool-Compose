package com.example.mycomposedemo.repo.models.pearno

import com.example.mycomposedemo.common.BasicResponseModel
import com.google.gson.annotations.SerializedName

data class HotNew(
    val code: Int,
    val `data`: List<NewsData>,
    val msg: String,
    val title: String
): BasicResponseModel(code, msg)

data class NewsData(
    val desc: String,
    val hot: String,
    val index: String,
    val mobilUrl: String,
    val pic: String,
    val title: String,
    val url: String
)

data class HotDouyin(
    val word : String,
    val lable : Int,
    @SerializedName("hot_value")
    val hotValue : Int
)