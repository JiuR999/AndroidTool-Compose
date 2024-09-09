package com.example.mycomposedemo.ui.sample

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnrememberedMutableState")
@Composable
fun StateSample() {
    var count = remember {
        mutableStateOf(1)
    }
    Text(text = "" + count.value,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable {
                count.value++
                Log.d("===", "" + count)
            }
            .background(color = MaterialTheme.colorScheme.secondaryContainer))

}

@Preview
@Composable
fun StateSamplePreview() {
    StateSample()
}