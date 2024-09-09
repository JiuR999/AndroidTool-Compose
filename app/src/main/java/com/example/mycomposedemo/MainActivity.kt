package com.example.mycomposedemo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DashboardCustomize
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycomposedemo.models.BottomItemData
import com.example.mycomposedemo.models.getDynamicColorScheme
import com.example.mycomposedemo.router.Screen
import com.example.mycomposedemo.ui.page.HomeScreen
import com.example.mycomposedemo.ui.theme.LocalIsUseDynamicColor
import com.example.mycomposedemo.ui.theme.LocalWindow
import com.example.mycomposedemo.ui.theme.MyAppTheme
import com.example.mycomposedemo.ui.theme.ThemeHelper
import com.example.mycomposedemo.ui.theme.ThemeSettingsProvider
import com.example.mycomposedemo.ui.theme.VariableTypography

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        /*        // 设置状态栏颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = Color.TRANSPARENT // 设置为透明
                }*/
        setContent {
            rememberNavController()
            val state = ThemeHelper.AppSettingsStateFlow.collectAsState()
            ThemeSettingsProvider {
                MyAppTheme(
                    colorScheme = if (LocalIsUseDynamicColor.current && Build.VERSION.SDK_INT > Build.VERSION_CODES.S) dynamicLightColorScheme(
                        LocalContext.current
                    )
                    else getDynamicColorScheme(
                        Color(state.value.themeColorSeed),
                        state.value.darkTheme.isDarkTheme()
                    ),
                    typography = VariableTypography
                ) {
                    CompositionLocalProvider(LocalWindow provides window) {
                        HomeScreen()
                    }

                }
            }
        }


    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}
