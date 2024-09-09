package com.example.mycomposedemo.ui.components


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mycomposedemo.R
import com.example.mycomposedemo.repo.models.wallerpaper.Category
import com.example.mycomposedemo.repo.models.wallerpaper.WallerCategory
import com.example.mycomposedemo.router.Screen
import com.example.mycomposedemo.ui.util.Shapes
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PictrueCategoryCard(category: Category,
                        click : () -> Unit) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .width(108.dp)
        .height(120.dp)
        .clip(shape = MaterialTheme.shapes.large)
        .clickable {
            click()
        }) {
        /*Image(painter = painterResource(id = R.drawable.two), contentDescription = "示例",
            contentScale = ContentScale.Crop)*/
        AsyncImage(model = category.cover,
            contentDescription = category.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(if(category.name == "") 9f / 16f else 1f)
                .pointerInput(Unit){
                    detectTapGestures(onLongPress = {
                        Toast.makeText(context,"保存"+category.cover,Toast.LENGTH_SHORT).show()
                        saveImageToGallery(context, category.cover)
                    },
                       onTap = {
                           click()
                       })
                })
        if (category.name != "") {
            Card(modifier = Modifier
                .height(32.dp)
                .width(56.dp)
                .padding(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(0.6f)
                ),) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = category.name,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

fun saveImageToGallery(context: Context, cover: String) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        return
    }

    // 使用 Coil 加载图片并保存
    val imageRequest = ImageRequest.Builder(context)
        .data(cover)
        .build()

    val imageLoader = Coil.imageLoader(context)

    CoroutineScope(Dispatchers.IO).launch {
        val result = imageLoader.execute(imageRequest)
        when (result) {
            is SuccessResult -> {
                val drawable = result.drawable
                val bitmap = (drawable as BitmapDrawable).bitmap

                // 使用 ContentValues 插入图片
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.IS_PENDING, 1) // 设置为 pending
                }

                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                if (uri != null) {
                    context.contentResolver.openOutputStream(uri).use { outputStream ->
                        if (outputStream != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                    }

                    // 更新 ContentValues，设置 IS_PENDING 为 0
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(uri, values, null, null)
                    Looper.prepare()
                    Toast.makeText(context, "图片已保存", Toast.LENGTH_SHORT).show()
                } else {
                    Looper.prepare()
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(context, "加载图片失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview
@Composable
fun PictrueCategoryCardPreview() {
    val str = "{\n" +
            "        \"count\": 93572,\n" +
            "        \"ename\": \"animation\",\n" +
            "        \"rname\": \"动漫\",\n" +
            "        \"cover_temp\": \"56a221c969401b3f4aa6700a\",\n" +
            "        \"name\": \"动漫\",\n" +
            "        \"cover\": \"http://img5.adesk.com/66506d35e7bce714fb0d51f2?imageMogr2/thumbnail/!640x480r/gravity/Center/crop/640x480&sign=935bb3d16a69b7f8292ac6664b05f20b&t=66cd58eb\",\n" +
            "        \"rank\": 4,\n" +
            "        \"id\": \"4e4d610cdf714d2966000003\",\n" +
            "        \"icover\": \"5880889ae7bce7755f3607d9\",\n" +
            "        \"sn\": 2,\n" +
            "        \"atime\": 1291266057,\n" +
            "        \"type\": 1,\n" +
            "        \"filter\": [],\n" +
            "        \"picasso_cover\": \"66506d35e7bce714fb0d51f2\"\n" +
            "    }"
    val category = Gson().fromJson(str, Category::class.java)
    PictrueCategoryCard(category,
        click = {
            Toast.makeText(null,"点击了",Toast.LENGTH_SHORT).show()
        })
}

