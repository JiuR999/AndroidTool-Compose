package com.example.mycomposedemo.repo.models.wallerpaper

import com.google.gson.annotations.SerializedName

data class PictrueCategory(
    val atime: Int = 0,
    val count: Int = 0,
    val cover: String = "",
    val cover_temp: String = "",
    val ename: String = "",
    val filter: List<Any> = listOf(),
    val icover: String = "",
    val id: String = "",
    val name: String = "",
    val picasso_cover: String = "",
    val rank: Int = 0,
    val rname: String = "",
    val sn: Int = 0,
    val type: Int = 0
)
open class Category(
    open val name: String = "",
    open val cover: String = ""
)