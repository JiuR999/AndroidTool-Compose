package com.example.mycomposedemo.ui.page

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.repo.models.pearno.HotDouyin
import com.example.mycomposedemo.repo.models.pearno.HotNew
import com.example.mycomposedemo.repo.models.pearno.NewsData
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance
import com.example.mycomposedemo.ui.components.HotNewsCard
import com.example.mycomposedemo.ui.components.TabScreen
import com.example.mycomposedemo.utils.OkHttpUtils
import com.example.mycomposedemo.viewmodel.UiViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotNewPage(){
    val tabTitles = listOf("微博", "抖音", "百度", "知乎")

    TabScreen(tabTitles = tabTitles) {
        HorizontalPager(state = it,
            modifier = Modifier.fillMaxSize()) {
            var res by remember {
                mutableStateOf("")
            }
            var datas by remember { mutableStateOf<HotNew?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            var errorMessage by remember { mutableStateOf<String?>(null) }
            val viewModel = UiViewModel()
            val state = viewModel.uiState.collectAsState()
            LaunchedEffect(Unit) {
                try {
                    Log.d("当前TAb", "第" + it  +"页")
                    if (it == 1) {
                        withContext(Dispatchers.IO) {
                            res = OkHttpUtils.get("https://www.haotechs.cn/ljh-wx/api/douyinHot")
                        }
                        Log.d("返回数据", res)
                        val d = JSONObject(res).get("result").toString()
                        Log.d("获取数据", d)
                        val hotDouyins = Gson().fromJson(d, Array<HotDouyin>::class.java)
                        Log.d("抖音热搜", hotDouyins[0].hotValue.toString())
                        val hotNews = mutableListOf<NewsData>()
                        hotDouyins.forEachIndexed { index, hotDouyin ->
                            hotNews.add(
                                NewsData(
                                    desc = hotDouyin.word,
                                    hot = hotDouyin.hotValue.toString(),
                                    index = (index+1).toString(),
                                    mobilUrl = "",
                                    pic = "",
                                    title = hotDouyin.word,
                                    url = ""
                                )
                            )
                        }
                        datas = HotNew(code = 200, data = hotNews, msg = "success", title = "success")
                    } else {
                        datas = PearnoRetrofitInstance.api.getHotNews(tabTitles[it])
                    }
                } catch (e: Exception) {
                    errorMessage = e.message
                } finally {
                    isLoading = false
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                errorMessage?.let {
                    Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                } ?: run {
                    datas?.let {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(datas!!.data.size) {
                                HotNewsCard(newsData = datas!!.data[it])
                            }
                        }
                    } ?: run {
                        Text(text = "数据为空", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}