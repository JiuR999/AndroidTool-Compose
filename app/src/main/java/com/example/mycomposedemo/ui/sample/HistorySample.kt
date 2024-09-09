package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.ui.components.ShapedBackIcon
import com.example.mycomposedemo.viewmodel.UiViewModel


@Composable
fun HistorySample(data : List<String>,
                  modifier: Modifier = Modifier) {
    val vm = UiViewModel()
    val state = vm.uiState.collectAsState()
    vm.loadData("https://uapi.woobx.cn/app/histoday?month=8&day=20")

    when (state.value) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success<*> -> {
            val data = (state.value as UiState.Success<*>).data.toString()
            //HistorySample(data)
            Text(text = data)
        }

        is UiState.Empty -> TODO()
        is UiState.Error<*> -> TODO()
        is UiState.Initial -> TODO()
    }
    data.forEach { s ->
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // 水平居中对齐
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShapedBackIcon(icon = Icons.Outlined.Newspaper)
                    Text(text = s)
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}


@Preview
@Composable
fun HistorySamplePreview() {
    val data = listOf(    "1998年7月28日 东盟与对话伙伴国会议开幕",
        "1998年7月28日 艾滋病蔓延中国大陆所有省区",
        "1994年7月28日 我国与拉脱维亚关系正常化",
        "1990年7月28日 王震副主席再访北大荒",
        "1984年7月28日 第23届奥运会在洛杉矶举行",
        "1976年7月28日 唐山大地震",
        "1960年7月28日 英国女作家伏尼契逝世",
        "1949年7月28日 英舰“紫石英”号逃出长江口",
        "1938年7月28日 秘鲁总统前藤森诞辰",
        "1908年7月28日 我国最早设立的国家储蓄银行",
        "1878年7月28日 清政府与美国签订《中美续增条约》",
        "1841年7月28日 道光皇帝谕令沿海各省撤兵",
        "1804年7月28日 德国唯物主义哲学家费尔巴哈诞辰",
        "1794年7月28日 罗伯斯庇尔被处死")
    HistorySample(data)
}
