package com.example.mycomposedemo.ui.sample

import android.graphics.drawable.PaintDrawable
import android.text.TextShaper
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldSample() {
    var value by remember {
        mutableStateOf("")
    }
    TextField(value = value, onValueChange = {
        value = it
    },
        label = {
            Text(text = "请输入账号")
        })
}


@Preview
@Composable
fun TextFieldSamplePreview() {
    var value by remember {
        mutableStateOf("")
    }
    TextField(value = value, onValueChange = {
        value = it
    },
        label = {
            Text(text = "请输入账号")
        }, leadingIcon = {
            Icon(imageVector = Icons.TwoTone.AccountCircle, contentDescription = "")
        },)
}