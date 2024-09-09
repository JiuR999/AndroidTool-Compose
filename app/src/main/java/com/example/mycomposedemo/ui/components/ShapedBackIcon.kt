package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.ui.util.Shapes
import com.example.mycomposedemo.utils.RoundedStarShape


@Composable
fun ShapedBackIcon(icon : ImageVector,
                   color : Color = MaterialTheme.colorScheme.secondaryContainer,
                   size : Dp = 36.dp,
                   shape : Shape = MaterialTheme.shapes.extraLarge,
                   modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(color = color,
        shape = shape)
        .padding(4.dp)
        .size(size),
        ) {
        Icon(icon,
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
                .size(20.dp))
    }
}

@Preview
@Composable
fun RoundBackIconPreview() {
    ShapedBackIcon(Icons.Outlined.MusicNote,
        shape = Shapes.Rounded4StarShape)
}
