package com.example.mycomposedemo.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddBox
import androidx.compose.ui.graphics.vector.ImageVector

data class GroupModel(
    val title: String,
    val datas : List<NavigationModel>
)

data class NavigationModel(
    val title: String,
    val subTitle: String,
    val icon: ImageVector = Icons.TwoTone.AddBox,
    val router: String,
)
