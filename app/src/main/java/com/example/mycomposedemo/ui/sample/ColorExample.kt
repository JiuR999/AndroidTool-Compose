package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ColorExample() {
    val monetColors = listOf(
        Color(0xFFB3CDE0), // 淡蓝色
        Color(0xFF6EB43F), // 草绿色
        Color(0xFFFFD700), // 明亮的黄色
        Color(0xFFFFA07A), // 淡粉色
        Color(0xFF8A2BE2)  // 紫色
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text("莫奈色彩调色板", style = MaterialTheme.typography.bodySmall)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (color in monetColors) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color)
                        .border(2.dp, Color.Black)
                )
            }
        }
    }
}

@Preview
@Composable
fun ColorExamplePreview() {
    ColorExample()
}
