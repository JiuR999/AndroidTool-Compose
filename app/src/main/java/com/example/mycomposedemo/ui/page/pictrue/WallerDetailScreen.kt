package com.example.mycomposedemo.ui.page.pictrue


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.repo.models.wallerpaper.PictrueCategory
import com.example.mycomposedemo.repo.models.wallerpaper.WallerDetail
import com.example.mycomposedemo.repo.net.PictureRetrofitInstance
import com.example.mycomposedemo.ui.components.PictrueCategoryCard
import com.example.mycomposedemo.ui.util.Dimensions

@Composable
fun WallerDetailScreen(id : String = "4e4d610cdf714d2966000003") {
    var datas by remember {
        mutableStateOf<WallerDetail?>(null)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        datas = PictureRetrofitInstance.api.getWallerDetail(id)
        isLoading = false
    }
    
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        datas?.res?.vertical?.let {
            val datas = it
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(Dimensions.DefaultPadding)
            ) {

                items(it.size) { index ->
                    val value = datas[index]
                    val pictrueCategory = PictrueCategory(
                        id = value.id,
                        cover = value.img,
                        type = value.source_type.length)
                    PictrueCategoryCard(pictrueCategory = pictrueCategory,
                        click = {

                        })
                }
            }
        } ?: run { 
            Text(text = "空数据")
        }
    }
}

@Preview
@Composable
fun WallerDetailScreenPreview() {
    WallerDetailScreen()
}

