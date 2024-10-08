package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.ui.util.Dimensions
import kotlinx.coroutines.delay

@Composable
fun CircleProgressSample() {
    val progress = 100
    var count by remember {
        mutableStateOf(0)
    }
    var currentProgress by remember {
        mutableStateOf(0)
    }
    var lastProgress by remember {
        mutableStateOf(-90f)
    }
    val perValue = 360f / progress
    var isLoading = true

    LaunchedEffect(count) {
        while (isLoading) {
            lastProgress = currentProgress * perValue
            currentProgress += 10
            delay(100L)
        }
    }
    Column {
        Surface(modifier = Modifier.size(60.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.SmallPadding), contentDescription = ""
            ) {
                val strokeWidth = size.width / 10
                val radius = (size.width - strokeWidth) / 2
                drawCircle(Color.LightGray, radius, center, style = Stroke(width = strokeWidth))
                //如果设置宽度 则需要偏移相应距离
                drawArc(
                    Color.Blue,
                    lastProgress%360f,
                    10 * perValue,
                    false,
                    topLeft = Offset(strokeWidth / 2, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(strokeWidth)
                )
            }

        }

        Button(onClick = { isLoading = false }) {
            Text(text = "暂停")
        }
    }
}

@Preview
@Composable
fun CircleProgressSamplePreview() {
    CircleProgressSample()
}

