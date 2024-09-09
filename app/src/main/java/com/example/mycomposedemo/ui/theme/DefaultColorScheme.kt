package com.example.mycomposedemo.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object DefaultColorScheme{
     val LightColorScheme = lightColorScheme(
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

    val DarkColorScheme = darkColorScheme()
}