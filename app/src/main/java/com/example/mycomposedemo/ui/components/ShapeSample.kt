package com.example.mycomposedemo.ui.components


import android.annotation.SuppressLint
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Cubic
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import kotlin.math.min

@SuppressLint("RememberReturnType")
@Composable
fun ShapeSample() {
    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        val shapes = listOf(
            // Line 1
            // Circle
            RoundedPolygon(4, rounding = CornerRounding(1f)),
            RoundedPolygon.star(12, innerRadius = .928f, rounding = CornerRounding(.1f)),

            // Clovers
            RoundedPolygon.star(4, innerRadius = .352f, rounding = CornerRounding(.32f)),
            RoundedPolygon.star(4, innerRadius = .152f, rounding = CornerRounding(.22f),
                innerRounding = CornerRounding.Unrounded),

            // Irregular Triangle
            //RoundedPolygon(vertices = trianglePoints, CornerRounding(.22f)),

            // Line 2

            // Rounded triangle
            RoundedPolygon(3, rounding = CornerRounding(.3f)),
            // Rounded+smoothed triangle
            RoundedPolygon(3, rounding = CornerRounding(.3f, 1f)),

            // CornerSE
            //RoundedPolygon(squarePoints(), perVertexRounding = cornerSERounding),

            // Unrounded Pentagon
            RoundedPolygon(5),

            // Unrounded 8-point star
            RoundedPolygon.star(8, innerRadius = .6f)
        )
        repeat(shapes.size) {
            val hexagon = remember {
                shapes[it]
            }
            val sizedShapes = remember(hexagon) { mutableMapOf<Size, List<Cubic>>() }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .drawWithContent {
                        drawContent()
                        val scale = min(size.width, size.height)
                        val shape = sizedShapes.getOrPut(size) { shapes[it].cubics}
                        drawPath(hexagon.toPath().asComposePath(), Color.Cyan)
                    }
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .size(200.dp)
            ) {
                Text(
                    "Hello Compose",
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}



@Preview
@Composable
fun ShapeSamplePreview() {
    ShapeSample()
}

