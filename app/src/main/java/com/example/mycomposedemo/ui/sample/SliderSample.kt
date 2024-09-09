package com.example.mycomposedemo.ui.sample


import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SliderSample() {
    var value by remember {
        mutableStateOf(0f)
    }
    Slider(value = value, onValueChange = {
        value = it
    })
}

@Preview
@Composable
fun SliderSamplePreview() {
    SliderSample()
}
