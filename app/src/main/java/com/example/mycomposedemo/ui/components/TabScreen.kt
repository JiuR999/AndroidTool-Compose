package com.example.mycomposedemo.ui.components


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposedemo.repo.models.pearno.HotNew
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance
import kotlinx.coroutines.launch

/**
 * @author: Zhang
 * @date: 2024/8/19
 */
@OptIn(ExperimentalFoundationApi::class)
/**
 * TabScreen
 * @param tabTitles 标签标题
 * @param modifier 修饰符
 * @param content 具体内容
 */
@Composable
fun TabScreen(tabTitles : List<String>,
              modifier: Modifier = Modifier,
              content :@Composable (state : PagerState) -> Unit) {
    val pageState = rememberPagerState {
        tabTitles.size
    }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier
        .fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pageState.currentPage,
            indicator = { positions ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .tabIndicatorOffset(positions[pageState.currentPage])
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            },
        ) {
            tabTitles.forEachIndexed { index, it ->
                Tab(
                    text = {
                        Text(
                            text = it,
                            fontSize = if (index == pageState.currentPage) 16.sp else 14.sp,
                            fontWeight = if (index == pageState.currentPage) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    selected = pageState.currentPage == index,
                    onClick = { scope.launch { pageState.animateScrollToPage(index) } }
                )
            }
        }

        content(pageState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TabScreenPreview() {
    val tabTitles = listOf("微博", "百度", "知乎")
    TabScreen(tabTitles) {
        HorizontalPager(state = it, modifier = Modifier.fillMaxSize()) {
            var datas by remember { mutableStateOf<HotNew?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                try {
                    Log.d("当前TAb", "第" + it  +"页")
                    datas = PearnoRetrofitInstance.api.getHotNews(tabTitles[it])
                } catch (e: Exception) {
                    errorMessage = e.message
                } finally {
                    isLoading = false
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Center))
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
                        Text(text = "No users found", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TabContent(content: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Center
    ) {
        Text(text = content)
    }
}
