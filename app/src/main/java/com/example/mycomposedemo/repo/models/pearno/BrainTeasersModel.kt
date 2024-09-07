package com.example.mycomposedemo.repo.models.pearno

import com.example.mycomposedemo.common.BasicResponseModel

data class BrainTeasersModel(
    val api_source: String = "",
    val code: Int = 400,
    val `data`: Data = Data(),
    val msg: String = ""
):BasicResponseModel(code, msg)

data class Data(
    val answer: String = "",
    val question: String = ""
)