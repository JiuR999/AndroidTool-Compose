package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.models.CustomThemeColors
import com.example.mycomposedemo.models.PaletteStyle
import com.example.mycomposedemo.models.getDynamicColorScheme
import com.example.mycomposedemo.ui.components.ShapedBackIcon


@Composable
fun CustomThemeSample() {

}

@Composable
fun CustomMaterialTheme(primaryColor: Color, content: @Composable () -> Unit) {
    val colors = generateMaterial3Theme(primaryColor)
    val colorScheme = getDynamicColorScheme(primaryColor, isDark = false, PaletteStyle.Monochrome)
/*    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = colors.primary,
            onPrimary = colors.onPrimary,
            secondary = colors.secondary,
            onSecondary = colors.onSecondary,
            background = colors.background,
            onBackground = colors.onBackground,
            surface = colors.surface,
            onSurface = colors.onSurface
        ),
        content = content
    )*/
    MaterialTheme(colorScheme = colorScheme,
        content = content)
}


@Preview
@Composable
fun CustomThemeSamplePreview() {
    CustomMaterialTheme(primaryColor = Color.Green) { // 自定义主色
        val primaryColor = Color.Green
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("欢迎使用自定义主题", style = MaterialTheme.typography.headlineMedium)
                Row {

                    PaletteStyle.entries.forEach {
                        val colorScheme = getDynamicColorScheme(primaryColor, isDark = false, it)
                        Column {
                            Button(onClick = { /* TODO */ }) {
                                Text("点击我")
                            }
                            var colorScheme = colorScheme
                            ShapedBackIcon(icon = Icons.Outlined.MusicNote,
                                color = colorScheme.secondary)
                            ShapedBackIcon(icon = Icons.Outlined.MusicNote,
                                color = colorScheme.secondaryContainer)
                            ShapedBackIcon(icon = Icons.Outlined.MusicNote,
                                color = colorScheme.primaryContainer)
                            ShapedBackIcon(icon = Icons.Outlined.MusicNote,
                                color = colorScheme.primary)
                            ShapedBackIcon(icon = Icons.Outlined.MusicNote,
                                color = colorScheme.tertiaryContainer)
                        }
                    }

                }
            }
        }
    }
}

fun generateMaterial3Theme(primaryColor: Color): CustomThemeColors {
    // 这里可以根据需要计算其他颜色
    val onPrimary = Color.White
    val secondaryColor = primaryColor.copy(alpha = 0.7f)
    val onSecondary = Color.Black
    val background = Color(0xFFF5F5F5)
    val onBackground = Color.Black
    val surface = Color.White
    val onSurface = Color.Black

    return CustomThemeColors(
        primary = primaryColor,
        onPrimary = onPrimary,
        secondary = secondaryColor,
        onSecondary = onSecondary,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface
    )
}
