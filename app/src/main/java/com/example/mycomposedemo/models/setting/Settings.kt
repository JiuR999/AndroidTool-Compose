package com.example.mycomposedemo.models.setting

data class Settings(
    var isDarkMode: Boolean = false,
    var notificationsEnabled: Boolean = true,
    var username: String = ""
)