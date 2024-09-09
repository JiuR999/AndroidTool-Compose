package com.example.mycomposedemo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccessTime
import androidx.compose.material.icons.twotone.Chat
import androidx.compose.material.icons.twotone.Movie
import androidx.compose.material.icons.twotone.Newspaper
import androidx.compose.material.icons.twotone.Topic
import androidx.compose.material.icons.twotone.Wallpaper
import androidx.lifecycle.ViewModel
import com.example.mycomposedemo.models.GroupModel
import com.example.mycomposedemo.models.NavigationModel
import com.example.mycomposedemo.router.Screen

class HomeViewModel : ViewModel() {
    var functions : List<GroupModel> = emptyList()
        get() {
            return listOf(
                GroupModel(
                    title = "推荐",
                    datas = getRecommandFunctions()
                ),
                GroupModel(
                title = "资讯",
                datas = getInfoFunctions()
            ),
                GroupModel(
                    title = "文本",
                    datas = getTextFunctions()
                ),
            )
        }

}

internal fun getRecommandFunctions(): List<NavigationModel> {
    return listOf(
        NavigationModel(
            title = "壁纸大全",
            subTitle = "涵盖海量精选分类壁纸",
            icon = Icons.TwoTone.Wallpaper,
            router = Screen.WallerPaper.router
        ),
    )
}

internal fun getTextFunctions(): List<NavigationModel> {
    return listOf(
        NavigationModel(
            title = "疯狂星期四",
            subTitle = "随机一句疯狂星期四文案",
            icon = Icons.TwoTone.Topic,
            router = Screen.CrazyThursday.router
        ),
    )
}

internal fun getInfoFunctions(): List<NavigationModel> {
    return listOf(
        NavigationModel(
            title = "News",
            subTitle = "汇集三大平台热搜",
            icon = Icons.TwoTone.Newspaper,
            router = Screen.Hotnews.router
        ),
        NavigationModel(
            title = "GPT",
            subTitle = "与大模型开始聊天吧",
            icon = Icons.TwoTone.Chat,
            router = Screen.Chat.router
        ),
        NavigationModel(
            title = "Movie",
            subTitle = "院线热搜电影推荐",
            icon = Icons.TwoTone.Movie,
            router = Screen.Movie.router
        ),
        NavigationModel(
            title = "History",
            subTitle = "看看历史上的今天",
            icon = Icons.TwoTone.AccessTime,
            router = Screen.History.router
        ),
    )
}