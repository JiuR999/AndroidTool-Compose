package com.example.mycomposedemo.ui.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.models.PaletteStyle
import com.example.mycomposedemo.models.getDynamicColorScheme
import com.example.mycomposedemo.ui.theme.LocalColorScheme
import com.example.mycomposedemo.ui.theme.LocalIsUseDynamicColor
import com.example.mycomposedemo.ui.theme.LocalPaletteStyleIndex
import com.example.mycomposedemo.ui.theme.LocalSeedColor
import com.example.mycomposedemo.ui.theme.ThemeHelper
import com.example.mycomposedemo.ui.theme.ThemeHelper.modifyThemeSeedColor
import com.example.mycomposedemo.ui.theme.ThemeHelper.recoveryDefaultTheme
import com.example.mycomposedemo.ui.theme.ThemeHelper.switchDynamicColor
import com.example.mycomposedemo.ui.theme.ThemeSettings
import com.example.mycomposedemo.ui.util.RippleAnimationState
import kotlinx.coroutines.CoroutineScope

/**
 * 对于一种颜色种子，生成了多种不同的风格
 */
@Composable
fun RowScope.ColorButtons(
    color: Color,
    scope: CoroutineScope,
    rippleAnimationState: RippleAnimationState,
    themeState: State<ThemeSettings>
) {
    val isDark = themeState.value.darkTheme.isDarkTheme()
    val contrastValue = ThemeHelper.currentThemeContrastValue()
    val defaultColor = LocalColorScheme.current
    listOf(
        PaletteStyle.TonalSpot,
        PaletteStyle.Neutral,
        PaletteStyle.Vibrant,
        PaletteStyle.Expressive,
        PaletteStyle.Rainbow,
        PaletteStyle.FruitSalad,
        PaletteStyle.Monochrome,
        PaletteStyle.Fidelity
    ).forEachIndexed { index, style: PaletteStyle ->
        ColorButton(
            color = color,
            index = index,
            tonalStyle = style,
            scope = scope,
            isDark = isDark,
            contrastValue = contrastValue,
            rippleAnimationState = rippleAnimationState,
            defaultColor = defaultColor,
        )
    }
}

@Composable
fun RowScope.ColorButton(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    index: Int = 0,
    tonalStyle: PaletteStyle = PaletteStyle.TonalSpot,
    scope: CoroutineScope,
    isDark: Boolean,
    contrastValue: Double,
    rippleAnimationState: RippleAnimationState,
    defaultColor: ColorScheme,
) {
    var tonalPalettes by remember {
        mutableStateOf(defaultColor)
    }
    val TAG = "ColorButtons"
    LaunchedEffect(key1 = isDark, key2 = contrastValue, block = {
        tonalPalettes = getDynamicColorScheme(
            color,
            isDark,
            tonalStyle,
            contrastValue
        )
    })
    Log.e(TAG, LocalIsUseDynamicColor.current.toString())
    Log.e(TAG, (LocalSeedColor.current == color.toArgb()).toString())
    Log.e(TAG, "${ThemeHelper.paletteStyleInt}/${index}")
    val isSelect =
        !LocalIsUseDynamicColor.current
                && LocalSeedColor.current == color.toArgb()
                && LocalPaletteStyleIndex.current == index
    ColorButtonImpl(modifier = modifier, colorScheme = tonalPalettes, isSelected = { isSelect }) {
        rippleAnimationState.change {
            scope.switchDynamicColor(enabled = false)
            scope.modifyThemeSeedColor(color.toArgb(), index)
            Log.e(TAG,"修改主题颜色为${color.toArgb()},上一个调色板${ThemeHelper.paletteStyleInt},使用调色板${index}")
            scope.recoveryDefaultTheme(useDefaultTheme = false)
        }
    }

}

@Composable
fun RowScope.ColorButtonImpl(
    modifier: Modifier = Modifier,
    isSelected: () -> Boolean = { false },
    colorScheme: ColorScheme,
    onClick: () -> Unit = {}
) {
    val containerSize by animateDpAsState(
        targetValue = if (isSelected.invoke()) 28.dp else 0.dp,
        label = "ColorButtonContainerSize"
    )
    val iconSize by animateDpAsState(
        targetValue = if (isSelected.invoke()) 16.dp else 0.dp,
        label = "ColorButtonIconSize"
    )

    Surface(
        modifier = modifier
            .padding(4.dp)
            .sizeIn(maxHeight = 80.dp, maxWidth = 80.dp, minHeight = 64.dp, minWidth = 64.dp)
            .weight(1f, false)
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        color =MaterialTheme.colorScheme.tertiaryContainer,
        onClick = onClick
    ) {
        val color1 = colorScheme.primary
        val color2 = colorScheme.secondaryContainer
        val color3 = colorScheme.tertiary
        Box(Modifier.fillMaxSize()) {
            Box(modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
                .drawBehind { drawCircle(color1) }
                .align(Alignment.Center)) {
                Surface(
                    color = color2, modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(24.dp)
                ) {}
                Surface(
                    color = color3, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                ) {}
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .size(containerSize)
                        .drawBehind { drawCircle(colorScheme.primaryContainer) },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(iconSize)
                            .align(Alignment.Center),
                        tint = colorScheme.onPrimaryContainer
                    )
                }

            }
        }
    }
}

