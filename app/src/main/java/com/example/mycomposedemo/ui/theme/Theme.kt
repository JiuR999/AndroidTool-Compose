package com.example.mycomposedemo.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.mycomposedemo.models.PaletteStyle
import com.example.mycomposedemo.models.getDynamicColorScheme
import com.google.android.material.color.MaterialColors

fun Color.applyOpacity(enabled: Boolean): Color {
    return if (enabled) this else this.copy(alpha = 0.62f)
}

@Composable
fun Color.harmonizeWith(other: Color) =
    Color(MaterialColors.harmonize(this.toArgb(), other.toArgb()))

@Composable
fun Color.harmonizeWithPrimary(): Color =
    this.harmonizeWith(other = MaterialTheme.colorScheme.primary)


tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFECEEF7),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)
@Composable
fun PreviewThemeLight(
    color: Color = Color(ThemeHelper.DEFAULT_COLOR_SEED),
    content: @Composable () -> Unit
) {
    val colorsScheme = getDynamicColorScheme(color, false, PaletteStyle.values()[0], 0.0)
    MaterialTheme(
        colorScheme = colorsScheme,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MyAppTheme(
    //darkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    typography: Typography = Typography,
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

