package com.example.mycomposedemo.repo.models.pearno

data class XHSImageModel(
    val api_source: String = "",
    val code: Int = 0,
    val `data`: ImageData = ImageData(),
    val msg: String = "",
    val url: String = ""
)

data class ImageData(
    val desc: Any = "",
    val img_lists: List<String> = emptyList()
)