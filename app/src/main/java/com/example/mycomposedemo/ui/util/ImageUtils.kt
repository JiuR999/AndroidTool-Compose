package com.example.mycomposedemo.ui.util

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ImageUtils {
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
            Looper.prepare()
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

                        Toast.makeText(context, "图片已保存", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show()
                    }
                } else -> {
                    Toast.makeText(context, "加载图片${cover}失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}