package com.example.mycomposedemo.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object TextUtils {
    fun CopyToClipboard(context: Context, content: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", content)
        clipboardManager.setPrimaryClip(clip)
    }
}