package com.example.mycomposedemo.ui.sample


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.twotone.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposedemo.R
import com.example.mycomposedemo.models.getDynamicColorScheme
import com.example.mycomposedemo.ui.components.MineMsg
import com.example.mycomposedemo.ui.components.OtherMsg
import com.example.mycomposedemo.ui.components.ShapedBackIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatSample() {
    val colorSchem = MaterialTheme.colorScheme
    var msg by remember {
        mutableStateOf("")
    }
    val messages = remember {
        mutableStateListOf("我是机器人", "Can i help you!", "我开始入门Compose啦")
    }

    val scheme = getDynamicColorScheme(Color.Green, false)
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.menu), contentDescription = "menu", modifier = Modifier.size(24.dp), tint = LocalContentColor.current.copy(0.8f))
                },

                actions = {
                    Icon(Icons.Filled.Edit, contentDescription = "menu", Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Filled.MoreVert, contentDescription = "menu")
                },
                title = {
                    // 使用 Spacer 设置标题与导航图标之间的间距
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(16.dp)) // 设置间距
                        Text(text = "ChatGPT")
                    }
                })
        })
    { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
            //.systemBarsPadding(),
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                items(messages.size) {

                    if (it % 2 == 0) {
                        OtherMsg(text = messages[it])
                    } else {
                        MineMsg(text = messages[it])
                    }
                    /*                ListItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        headlineContent = { Text(text = "ChatGPT") },
                                        supportingContent = { Text(text = messages[it]) },
                                        leadingContent = {
                                            RoundBackIcon(icon = Icons.Outlined.Person, color = scheme.primaryContainer)
                                        },
                                        trailingContent = {
                                            Text(text = getCurrentTime())
                                        })
                                    Divider(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        thickness = 1.dp) // 添加分割线*/
                }
            }

            Row(
                modifier = Modifier
                    .height(64.dp)
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                ShapedBackIcon(icon = Icons.Default.Add)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(value = msg,
                    onValueChange = {
                        msg = it
                    },

                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 4.dp)
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent, // 去掉聚焦时的底部线
                        unfocusedIndicatorColor = Color.Transparent, // 去掉未聚焦时的底部线
                        focusedContainerColor = colorSchem.primaryContainer, // 聚焦时的背景颜色
                        unfocusedContainerColor = colorSchem.surfaceVariant // 失去焦点时的背景颜色
                    ),
                    placeholder = {
                        if (msg == "") {
                            Text(
                                text = "请输入会话...",
                                style = TextStyle(fontSize = 13.sp), // 调整字体大小
                                )
                        }
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    trailingIcon = {
                        if (msg != "") {
                            IconButton(onClick = { msg = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))

                ShapedBackIcon(icon = Icons.TwoTone.Send,
                    modifier = Modifier
                        .clickable {
                            messages.add(msg)
                            msg = ""
                        })

                Spacer(modifier = Modifier.width(8.dp))
/*                Icon(
                    imageVector = Icons.TwoTone.Send,
                    contentDescription = null,
                    tint = colorSchem.secondary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                        .clickable {
                            messages.add(msg)
                            msg = ""
                        }
                )*/
            }


            /*            //Basic高度自定义
                        BasicTextField(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .padding(8.dp),
                            value = msg,
                            onValueChange = {
                                msg = it
                            },
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                                        .padding(8.dp)
                                ) {
                                    if (msg.isEmpty()) {
                                        Text("输入消息...")
                                    }
                                    innerTextField()
                                }
                            })*/

        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ChatSamplePreview() {
    ChatSample()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return LocalDateTime.now().format(formatter)
}