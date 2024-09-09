package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import com.example.mycomposedemo.R
import com.example.mycomposedemo.utils.RoundedStarShape

@Composable
fun ShapeSample() {

Column {
    //curve 0.09
    val roundedStarShape = RoundedStarShape(
        sides = 4,
        curve = 0.16,
        rotation = 45f,
        iterations = 360
    )

    Surface(
        shape = roundedStarShape,
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0x70205C47))
                .size(200.dp),
            contentAlignment = Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_icon),
                contentDescription = "",
                modifier = Modifier.size(56.dp)
            )
        }
    }

    Box(
        modifier = Modifier
            .drawWithCache {
                val roundedPolygon = RoundedPolygon(
                    4,
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2,
                    rounding = CornerRounding(
                        size.minDimension / 10f,
                        smoothing = 0.1f
                    )
                )
                val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                onDrawBehind {
                    drawPath(roundedPolygonPath, color = Color.Blue)
                }
            }
            .fillMaxSize()
    )

    Box(
        modifier = Modifier
            .drawWithCache {
                val roundedPolygon = RoundedPolygon.star(4, innerRadius = .352f, rounding = CornerRounding(.32f))
                val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                onDrawBehind {
                    drawPath(roundedPolygonPath, color = Color.Blue)
                }
            }
            .fillMaxSize()
    )
}
}

@Preview
@Composable
fun ShapeSamplePreview() {
    ShapeSample()
}

