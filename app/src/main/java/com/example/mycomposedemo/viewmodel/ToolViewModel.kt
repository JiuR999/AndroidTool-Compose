package com.example.mycomposedemo.viewmodel

import com.example.mycomposedemo.models.NavigationModel
import com.example.mycomposedemo.router.Screen

class ToolViewModel {
    val textTool = getTextToolList()

    private fun getTextToolList(): List<NavigationModel> {
        return listOf(
            NavigationModel("随机一言", "随机一言", router = Screen.Hitokoto.router),
            NavigationModel("诗词一言", "诗词一言", router = Screen.Hitokoto.router + "i"),
            NavigationModel("网易云文案", "诗词一言", router = Screen.Hitokoto.router + "j"),
            NavigationModel("脑筋急转弯", "脑筋急转弯", router = Screen.BrainTeasers.router),
            NavigationModel("随机一文", "随机一文", router = "titokoto"),
            NavigationModel("愿神语录", "随机一文", router = "titokoto"),
            NavigationModel("六十秒读世界", "六十秒读世界", router = "titokoto"),
            NavigationModel("历史上的今天", "历史上的今天", router = Screen.History.router),
            NavigationModel("疯狂星期四文案", "疯狂星期四文案", router = Screen.CrazyThursday.router),
            NavigationModel("随机彩虹屁", "随机彩虹屁", router = "titokoto"),
        )
    }
}