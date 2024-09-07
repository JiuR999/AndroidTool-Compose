package com.example.mycomposedemo.models

data class History @JvmOverloads constructor(
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val detail : String = "",
    val title : String = "",
    val content : String = "",
    val cover : String? = null
)