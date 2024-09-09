package com.example.mycomposedemo.router

sealed class Screen(val router : String) {
    data object Home : Screen("home")
    data object Movie : Screen("movie")
    data object Chat : Screen("chat")
    data object Setting : Screen("setting")
    data object Hotnews : Screen("hotnews")
    data object History : Screen("history")
    data object WallerPaper : Screen("wallerpaper")
    data object SettingTheme : Screen("setting_theme")
    data object Tool : Screen("tool")
    data object Hitokoto : Screen("hitokoto?c=")
    data object CrazyThursday : Screen("crazy_thurday")
    data object BrainTeasers : Screen("brainteasers")
}