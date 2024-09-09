package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposedemo.ui.theme.ThemeHelper
import com.example.mycomposedemo.ui.util.rememberRippleAnimationState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeSelector() {
    val scope = rememberCoroutineScope()
    val themeSettingState = ThemeHelper.AppSettingsStateFlow.collectAsState()
    val rippleAnimationState = rememberRippleAnimationState()
    val colorList = listOf(Color.Green, Color.Cyan,Color.Yellow)
    //val colorList = ((0..11).map { it * 31.0 }).map { Color(Hct.from(it, 45.0, 45.0).toInt()) }
    FlowRow {
        colorList.forEach {
            ColorButtons(
                color = it,
                scope = scope,
                rippleAnimationState = rippleAnimationState,
                themeState = themeSettingState
            )
        }
    }
}

@Preview
@Composable
fun ThemeSelectorPreview() {
    ThemeSelector()
}

