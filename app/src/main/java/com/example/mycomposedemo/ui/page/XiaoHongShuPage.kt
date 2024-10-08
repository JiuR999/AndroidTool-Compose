package com.example.mycomposedemo.ui.page


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycomposedemo.repo.models.pearno.XHSImageModel
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance
import com.example.mycomposedemo.ui.components.AlertContentDialog
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.ui.util.ImageUtils
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun XiaoHongShuPage() {
    val context = LocalContext.current
    var url by remember { mutableStateOf("") }
    var coverUrl by remember { mutableStateOf("") }
    var isSaveDialogVisible by remember { mutableStateOf(false) }
    var images by remember { mutableStateOf(XHSImageModel()) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(Dimensions.DefaultPadding)
    ) {
        OutlinedTextField(url, {
            url = it
        },
            label = {
                Text("请输入作品链接")
            }, modifier = Modifier.fillMaxWidth(),
            suffix = {
                IconButton(onClick = {
                    url = ""
                }) {
                    Icon(imageVector = Icons.TwoTone.Clear, contentDescription = "清空")
                }
            })

        Button(onClick = {
            scope.launch {
                isLoading = true
                val regex = Pattern.compile("http:[/.\\w]+")
                val matcher = regex.matcher(url)
                if (matcher.find()) {
                    images = PearnoRetrofitInstance.api.getXHSImages(matcher.group())
                }

                isLoading = false
            }
        }, modifier = Modifier.align(Alignment.End)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.Send,
                    contentDescription = "解析",
                    modifier = Modifier.size(16.dp)
                )
                Text("解析")
            }
        }

        if (isLoading) {

            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        } else {
            LazyVerticalGrid(GridCells.Fixed(2)) {
                items(images.data.img_lists.size) {
                    var imgUrl = images.data.img_lists[it]
                    AsyncImage(
                        imgUrl, contentDescription = "图集",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(9f / 16f)
                            .padding(Dimensions.SmallPadding)
                            .pointerInput(Unit) {
                                detectTapGestures(onLongPress = {
                                    isSaveDialogVisible = true
                                    coverUrl = imgUrl
                                })
                            }
                    )
                }
            }
        }

        AnimatedVisibility(isSaveDialogVisible) {
            AlertContentDialog(onDismissRequest = {
                isSaveDialogVisible = false
            }, confirmButton = {

            }) {
                Text(text = "保存",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            ImageUtils.saveImageToGallery(context, coverUrl)
                            isSaveDialogVisible = false
                        })
            }
        }
    }
}

@Preview
@Composable
fun XiaoHongShuPagePreview() {
    XiaoHongShuPage()
}

