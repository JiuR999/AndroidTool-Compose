package com.example.mycomposedemo.ui.components


import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.mycomposedemo.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    images: List<Any>,
    previewPercent: Float = 0f,
    pageState : PagerState,
    modifier: Modifier = Modifier,
) {
    var height by remember {
        mutableStateOf(0.dp)
    }

    HorizontalPager(
        state = pageState,
        modifier = modifier.onGloballyPositioned {
            height = it.size.height.dp
        },
        contentPadding = if(previewPercent != 0f) PaddingValues(horizontal = height * previewPercent * 0.7f)
        else PaddingValues(16.dp)
    ) {
        //激活项的缩放过渡
        val imgScale by animateFloatAsState(
            targetValue = if (pageState.currentPage == it) 1.0f else 0.8f,
            animationSpec = tween(300), label = ""
        )
        val offset = animateFloatAsState(
            targetValue = if (pageState.currentPage == it) 0f else 1f,
            animationSpec = tween(durationMillis = 300)
        ).value

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .scale(imgScale)
                .clip(
                    MaterialTheme.shapes.large
                ),

            model = ImageRequest.Builder(LocalContext.current)
                .scale(Scale.FILL)
                .data(images[it])
                .build(),
            contentDescription = "图片$it",
            contentScale = ContentScale.FillBounds
        )

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun BannerPreview() {
    val images = listOf(
        R.drawable.two,
        R.drawable.three,
        R.drawable.jiur,
    )
    val ps = rememberPagerState {
        images.size
    }
    Banner(
        images,
        pageState = ps,
        modifier = Modifier
            .height(200.dp)
            .padding(vertical = 16.dp)
    )
}

