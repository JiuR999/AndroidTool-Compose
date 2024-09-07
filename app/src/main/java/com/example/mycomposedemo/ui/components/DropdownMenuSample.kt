package com.example.mycomposedemo.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun DropdownMenuSample() {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column() {
        IconButton(onClick = {
            expanded = !expanded
        }) {
            Icon(imageVector = Icons.TwoTone.Menu, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = {
                Log.i("======", "==========")
                expanded = false
            }, offset = DpOffset(x = 10.dp, y = 10.dp), properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.SecureOn,//设置安全级别，是否可以截屏
            )
        ) {
            DropdownMenuItem(onClick = { expanded = false },
                text = {
                        Text(text = "Menu0")
                })

            DropdownMenuItem(onClick = { expanded = false },
                text = {
                    Text(text = "Menu1")
                })

            DropdownMenuItem(onClick = { expanded = false },
                text = {
                    Text(text = "Menu2")
                })
        }
    }
}

@Preview
@Composable
fun test(){
    DropdownMenuSample()
}