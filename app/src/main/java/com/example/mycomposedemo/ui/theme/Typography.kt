package com.example.mycomposedemo.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.example.mycomposedemo.R

// In Typography.kt
@SuppressLint("NewApi")
val default = FontFamily(
    /*
    * This can be any font that makes sense
    */
    Font(
        R.font.literatavariablefont
    )
)
// VariableFontDimension.kt
object DisplayLargeVFConfig {
    const val WEIGHT = 900
    const val WIDTH = 30f
    const val SLANT = -6f
    const val ASCENDER_HEIGHT = 800f
    const val COUNTER_WIDTH = 500
}

@OptIn(ExperimentalTextApi::class)
val displayLargeFontFamily = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    FontFamily(
        Font(
            R.font.literatavariablefont,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(DisplayLargeVFConfig.WEIGHT),
                FontVariation.width(DisplayLargeVFConfig.WIDTH),
                FontVariation.slant(DisplayLargeVFConfig.SLANT),
            )
        )
    )
} else {
    default
}
