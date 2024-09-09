package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposedemo.R
import com.example.mycomposedemo.repo.models.pearno.NewsData


@OptIn(ExperimentalTextApi::class)
@Composable
fun HotNewsCard(
    newsData: NewsData,
    modifier: Modifier = Modifier,
) {

    val typography = MaterialTheme.typography

    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.back))

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            if (newsData.index != "1" && newsData.index != "2") {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape,
                        )

                ) {

                    Text(
                        text = newsData.index,
                        style = typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                }
            } else {
                    // 计算 Text 的高度
                    val textHeight =
                        with(LocalDensity.current) { 48.sp.toPx() } + with(LocalDensity.current) { 4.dp.toPx() }
/*                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .height(36.dp)
                            .align(Alignment.TopEnd)
                            .padding(top = 26.dp)
                            .background(
                                MaterialTheme.colorScheme.tertiaryContainer,
                                shape = MaterialTheme.shapes.medium
                            )
                    )*/
Box(modifier = Modifier.align(Alignment.CenterVertically)){
    Text(
        text = newsData.index,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        color = if (newsData.index == "1") Color.Red.copy(0.9f) else MaterialTheme.colorScheme.tertiaryContainer,
    )
}
            }

            Text(
                text = newsData.title,
                style = if (newsData.title.length < 15) typography.titleMedium else typography.titleSmall,

                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                Icons.TwoTone.LocalFireDepartment,
                tint = Color.Red.copy(0.9f),
                contentDescription = "热度",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = if (newsData.hot.contains("万")) {
                    newsData.hot
                } else {
                    "" + newsData.hot.toInt() / 10000 + "w"
                },
                style = typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

@Preview
@Composable
fun HotNewsSamplePreview() {
    val newsData = NewsData(
        index = "1",
        title = "习近平会见意大利总理梅洛尼",
        pic = "https://fyb-2.cdn.bcebos.com/hotboard_image/2ddef04b3e451c3456aa64b8d719a044",
        hot = "4913167",
        desc = "7月29日下午，国家主席习近平在北京钓鱼台国宾馆会见来华进行正式访问的意大利总理梅洛尼。",
        url = "https://www.baidu.com/s?wd=%E4%B9%A0%E8%BF%91%E5%B9%B3%E4%BC%9A%E8%A7%81%E6%84%8F%E5%A4%A7%E5%88%A9%E6%80%BB%E7%90%86%E6%A2%85%E6%B4%9B%E5%B0%BC&sa=fyb_news&rsv_dl=fyb_news",
        mobilUrl = "https://www.baidu.com/s?wd=%E4%B9%A0%E8%BF%91%E5%B9%B3%E4%BC%9A%E8%A7%81%E6%84%8F%E5%A4%A7%E5%88%A9%E6%80%BB%E7%90%86%E6%A2%85%E6%B4%9B%E5%B0%BC&sa=fyb_news&rsv_dl=fyb_news"
    )
    HotNewsCard(newsData)
}
