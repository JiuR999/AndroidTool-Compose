package com.example.mycomposedemo.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mycomposedemo.constants.ThemeStr
import com.example.mycomposedemo.models.boolM
import com.tencent.mmkv.MMKV
import java.time.format.DateTimeFormatter

object AppPreference {
    @RequiresApi(Build.VERSION_CODES.O)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val mk = MMKV.defaultMMKV()

    var isDark by mk.boolM(ThemeStr.dark_theme_mode)
}

class PrefKeyName {
    companion object {
        const val api_key: String = "api_key"
        const val play_plan: String = "play_plan"
        const val is_dark: String = "is_dark"
    }
}