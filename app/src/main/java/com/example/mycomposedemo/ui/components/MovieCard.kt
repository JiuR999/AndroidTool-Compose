package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycomposedemo.models.Discribe
import com.example.mycomposedemo.models.MovieDoub
import com.google.gson.Gson

@Composable
fun MovieCard(
    item: Discribe,
    coverHeight : Dp = 160.dp,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = modifier
            .clickable { onSelect() },
    ) {
        Column {
            if(item.cover?.isNotEmpty() == true) {
                AsyncImage(
                    model = item.cover,
                    contentDescription = null,
                    modifier = Modifier.height(coverHeight)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                text = item.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    var openAlertDialog by remember {
        mutableStateOf(false)
    }
    val mov = """
        {
        "episodes_info": "",
        "rate": "9.1",
        "cover_x": 4056,
        "title": "泳者之心",
        "url": "https://movie.douban.com/subject/26656728/",
        "playable": false,
        "cover": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2910815887.webp",
        "id": "26656728",
        "cover_y": 6009,
        "is_new": false
    }
    """.trimIndent()

    val Item = Gson().fromJson(mov, MovieDoub::class.java)
    val item1 = Discribe(title = Item.title, cover = Item.cover)
    MovieCard(item = item1, onSelect = {
        openAlertDialog = true
    })

}

