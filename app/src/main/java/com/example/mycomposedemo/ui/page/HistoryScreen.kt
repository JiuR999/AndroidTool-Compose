package com.example.mycomposedemo.ui.page


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.History
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.models.Discribe
import com.example.mycomposedemo.models.History
import com.example.mycomposedemo.ui.components.MovieCard
import com.example.mycomposedemo.ui.components.AlertTextDialog
import com.example.mycomposedemo.ui.sample.UiState
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.viewmodel.UiViewModel
import com.google.gson.Gson
import org.json.JSONObject

@Composable
fun HistoryScreen(
    month: String,
    day: String,
) {
    val vm = UiViewModel()
    val state by vm.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var item: History = History()
    var show by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        //vm.loadData("https://movie.douban.com/j/search_subjects?&type=movie&sort=recommend&tag=%E7%83%AD%E9%97%A8&page_limit=21&page_start=1")
        vm.loadData("https://uapi.woobx.cn/app/histoday?month=$month&day=$day")
    }
    //vm.loadData("https://movie.douban.com/j/search_subjects?&type=movie&sort=recommend&tag=%E7%83%AD%E9%97%A8&page_limit=21&page_start=1")
    Surface {

        when (state) {
            is UiState.Initial -> {
                Text(text = "初始化中")
            }

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UiState.Empty -> {
                Text(text = "空")
            }

            is UiState.Error<*> -> {
                Text(text = "Error")
            }

            is UiState.Success<*> -> {
                val data = (state as UiState.Success<*>).data
                val s = JSONObject(data as String).get("data").toString()
                val histories = Gson().fromJson(s, Array<History>::class.java).toList()
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(Dimensions.DefaultPadding),
                    verticalItemSpacing = 8.dp
                ) {
                    items(histories.size) {
                        val history = histories[it]
                        val discribe = Discribe(history.title, history.cover)
                        MovieCard(
                            item = discribe,
                            onSelect = {
                                item = history
                                show = true
                            },
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(8.dp)
                                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        )
                    }
                }

                if (show) {
                    AlertTextDialog(
                        onDismissRequest = { show = false },
                        context = LocalContext.current,
                        dialogTitle = item.year + "年" + item.month + "月" + item.day + "日",
                        dialogText = item.content,
                        icon = Icons.TwoTone.History
                    )
                }
            }

            else -> {}
        }
    }

}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(month = "8", day = "26")
}

