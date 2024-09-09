package com.example.mycomposedemo

import android.app.Application
import com.tencent.mmkv.MMKV

class AppCtx : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //mmkv初始化
        MMKV.initialize(this)
    }
    companion object {
        lateinit var instance: AppCtx
    }
}