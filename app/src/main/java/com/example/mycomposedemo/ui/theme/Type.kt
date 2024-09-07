package com.example.mycomposedemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val FONTFAMILY = displayLargeFontFamily
val VariableTypography = Typography(
        displayMedium = TextStyle(
                fontFamily = FONTFAMILY,
                fontWeight = FontWeight(400),
                fontSize = 45.sp,
                lineHeight = 52.0.sp,
                letterSpacing = 0.0.sp,
        ),
        displayLarge = TextStyle(
                fontFamily = FONTFAMILY,
                fontSize = 50.sp,
                lineHeight = 64.sp,
                letterSpacing = 0.sp,
                /***/
        ),
        titleMedium = TextStyle(
                fontFamily = FONTFAMILY,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.2.sp,
        )
)

val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
        )
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val preferenceTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, lineHeight = 24.sp,
        lineBreak = LineBreak.Paragraph,
)