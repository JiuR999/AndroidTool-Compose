package com.example.mycomposedemo.repo.api

import com.example.mycomposedemo.models.History
import com.example.mycomposedemo.repo.models.pearno.BrainTeasersModel
import com.example.mycomposedemo.repo.models.pearno.HotNew
import com.example.mycomposedemo.repo.models.pearno.KFCText
import com.example.mycomposedemo.router.Screen
import retrofit2.http.GET
import retrofit2.http.Query

interface PearnoApiService {
    //历史上的今天
    @GET("lsjt/?type=json")
    suspend fun getHistory(): History

    //聚合热搜
    @GET("social/hotlist.php")
    suspend fun getHotNews(@Query("type") type: String): HotNew

    //肯德基疯狂星期四文案
    @GET("kfc")
    suspend fun getKFC(): KFCText

    //脑筋急转弯
    @GET("brainteasers")
    suspend fun getBrainteasers(): BrainTeasersModel

    //舔狗日记
    @GET("jdyl/tiangou.php")
    suspend fun getTiangou(): String

    @GET("jdyl/qinghua.php")
    suspend fun getRomatic(): String

    @GET("caihongpi")
    suspend fun getRainbow() : String
}
