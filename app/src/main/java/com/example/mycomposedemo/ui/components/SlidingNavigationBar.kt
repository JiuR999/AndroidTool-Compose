package com.example.mycomposedemo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SlidingNavigationBar() {
    var isNavBarVisible by remember { mutableStateOf(true) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 主内容
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (isNavBarVisible) 56.dp else 0.dp) // 根据导航栏的可见性调整底部填充
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        offsetY += dragAmount
                        if (offsetY > 50) { // 向上滑动
                            isNavBarVisible = false
                            offsetY = 0f
                        } else if (offsetY < -50) { // 向下滑动
                            isNavBarVisible = true
                            offsetY = 0f
                        }
                    }
                }
        ) {
            // 这里是你的主内容
            for (i in 1..100) {
                Text(text = "项目 #$i", modifier = Modifier.padding(16.dp))
            }
        }

        // 自定义导航栏
        AnimatedVisibility(visible = isNavBarVisible) {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text("导航栏", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
@Preview
fun p(){
    SlidingNavigationBar()
}