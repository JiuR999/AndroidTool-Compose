package com.example.mycomposedemo.models

data class MovieDoub(
    val cover: String,
    val cover_x: Int,
    val cover_y: Int,
    val episodes_info: String,
    val id: String,
    val is_new: Boolean,
    val playable: Boolean,
    val rate: String,
    val title: String,
    val url: String
) {
    constructor() : this( // 初始化所有属性的默认值
        cover = "",
        cover_x = 0,
        cover_y = 0,
        episodes_info = "",
        id = "",
        is_new = false,
        playable = false,
        rate = "",
        title = "",
        url = ""
    )
}