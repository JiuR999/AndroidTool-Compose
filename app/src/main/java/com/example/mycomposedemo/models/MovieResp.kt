package com.example.mycomposedemo.models


data class MovieResp(
    val code: Int,
    val limit: String,
    val list: List<Movie>,
    val msg: String,
    val page: Int,
    val pagecount: Int,
    val total: Int,
    val subjects : List<MovieDoub>
)