package com.example.mycomposedemo.ui.page


import android.util.Log
import android.view.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ColorLens
import androidx.compose.material.icons.twotone.Colorize
import androidx.compose.material.icons.twotone.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsCheckbox
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import com.alorma.compose.settings.ui.SettingsTriStateCheckbox
import com.example.mycomposedemo.common.AppPreference
import com.example.mycomposedemo.models.setting.Settings
import com.example.mycomposedemo.ui.components.ColorButtons
import com.example.mycomposedemo.ui.theme.DarkThemePrefs
import com.example.mycomposedemo.ui.theme.LocalPaletteStyleIndex
import com.example.mycomposedemo.ui.theme.ThemeHelper
import com.example.mycomposedemo.ui.theme.ThemeHelper.modifyDarkThemePreference
import com.example.mycomposedemo.ui.theme.ThemeHelper.switchDynamicColor
import com.example.mycomposedemo.ui.theme.ThemeSettingsProvider
import com.example.mycomposedemo.ui.util.autoRippleAnimation
import com.example.mycomposedemo.ui.util.rememberRippleAnimationState

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SettingScreen(window: Window) {
    val TAG = "SettingScreen"
    val colorList = listOf(Color.Green, Color.Cyan, Color.Yellow)
    var setting by remember {
        mutableStateOf(Settings())
    }
    val pageState = rememberPagerState {
        colorList.size
    }
    val themeSettingState = ThemeHelper.AppSettingsStateFlow.collectAsState()
    val themeSetting = themeSettingState.value
    //var example by mv.strM("kk")

    var isDark = themeSetting.darkTheme.isDarkTheme()
    val scope = rememberCoroutineScope()
    val rippleAnimationState = rememberRippleAnimationState {
        animTime = 1000
        moveUpSystemBarInsts = false
    }
    //val window=LocalContext.current?:throw IllegalArgumentException()
    Scaffold(
        modifier = Modifier.autoRippleAnimation(window, rippleAnimationState)
    ) {
        Column(modifier = Modifier.padding(it)) {


            SettingsGroup(title = { Text(text = "主题设置") }) {
                Card(
                    elevation = CardDefaults.elevatedCardElevation(),
                    modifier = Modifier.padding(8.dp)
                ) {
                    SettingsSwitch(
                        state = setting.isDarkMode,
                        title = { Text(text = "动态取色") },
                        icon = {
                            Icon(
                                imageVector = Icons.TwoTone.Colorize,
                                contentDescription = null
                            )
                        }) {
                        setting = setting.copy(isDarkMode = it)
                        Log.e(
                            TAG,
                            "MMKV存入使用动态色彩" + it.toString()
                        )
                        scope.switchDynamicColor(it)

                    }
                    SettingsSwitch(
                        state = isDark,
                        title = { Text(text = "夜间模式") },
                        icon = {
                            Icon(
                                imageVector = Icons.TwoTone.DarkMode,
                                contentDescription = null
                            )
                        }) {

                        isDark = it
                        AppPreference.isDark = it
                        rippleAnimationState.change {
                            scope.modifyDarkThemePreference(darkThemeMode = if (it == true) DarkThemePrefs.ON else DarkThemePrefs.OFF)
                        }
                        Log.e(
                            TAG,
                            "MMKV存入夜间模式是否启用" + AppPreference.isDark.toString()
                        )
                    }

                    SettingsMenuLink(
                        title = { Text(text = "主题色彩") },
                        icon = {
                            Icon(
                                imageVector = Icons.TwoTone.ColorLens,
                                contentDescription = null
                            )
                        },
                        onClick = { },
                    )

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clearAndSetSemantics { },
                        state = pageState
                    ) { pageIndex ->
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            maxItemsInEachRow = 4,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            ColorButtons(
                                color = colorList[pageIndex],
                                scope = scope,
                                rippleAnimationState = rippleAnimationState,
                                themeState = themeSettingState
                            )
                        }
                    }

                    Row(
                        Modifier
                            .height(50.dp)
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pageState.pageCount) { iteration ->
                            val color =
                                if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(10.dp)

                            )
                        }
                    }

                }
            }

            SettingsTriStateCheckbox(
                state = false,
                title = { Text(text = "开启缓存") },
                subtitle = { Text(text = "Some extra text") })

            Surface {
                SettingsGroup(
                    title = {
                        Text(text = "SettingsGroupPreview")
                    },
                    enabled = true,
                ) {
                    SettingsMenuLink(
                        title = { Text(text = "Menu link tile") },
                        onClick = {},
                    )
                    SettingsCheckbox(
                        state = true,
                        title = { Text(text = "Checkbox tile") },
                        onCheckedChange = {},
                    )
                    SettingsSwitch(
                        state = true,
                        title = { Text(text = "Switch tile") },
                        onCheckedChange = {},
                    )
                }
            }
        }
    }


}

@Preview
@Composable
fun SettingScreenPreview() {
    ThemeSettingsProvider {
        //SettingScreen(window)
    }
}

