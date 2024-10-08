package com.example.mycomposedemo.router

sealed class Screen(val router: String) {
    //    主页
    data object Home : Screen("home")

    //    热门电影
    data object Movie : Screen("movie")
    data object Chat : Screen("chat")

    //    设置页面
    data object Setting : Screen("setting")

    //    热搜新闻
    data object Hotnews : Screen("hotnews")

    //    历史上的今天
    data object History : Screen("history")

    //    壁纸大全
    data object WallerPaper : Screen("wallerpaper")

    //    主题设置
    data object SettingTheme : Screen("setting_theme")

    //    工具箱大全
    data object Tool : Screen("tool")

    //    随机一言
    data object Hitokoto : Screen("hitokoto?c=")

    //    疯狂星期四文案
    data object CrazyThursday : Screen("crazy_thurday")

    //    脑筋急转弯
    data object BrainTeasers : Screen("brainteasers")
    //小红书图集解析
    data object XiaoHongShu : Screen("xhongshu")

    //经典情话
    data object Romantic : Screen("romantic")

    //    彩虹屁
    data object Rainbow : Screen("rainbow")

    //    舔狗日记
    data object Tiangou : Screen("tiangou")
    //60s读世界
    data object LearnWorld : Screen("learn_world")
}