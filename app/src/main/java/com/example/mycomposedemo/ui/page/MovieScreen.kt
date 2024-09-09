package com.example.mycomposedemo.ui.page


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.analytics.AnalyticsListener
import coil.compose.AsyncImage
import com.example.mycomposedemo.models.MovieDoub
import com.example.mycomposedemo.models.MovieResp
import com.example.mycomposedemo.ui.components.ShapedBackIcon
import com.example.mycomposedemo.ui.sample.UiState
import com.example.mycomposedemo.viewmodel.UiViewModel

import com.google.gson.Gson

import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MovieScreen() {

    val viewModel = UiViewModel()

    val state = viewModel.uiState.collectAsState()
    var title by remember {
        mutableStateOf("热门影视")
    }
    var showDetail by remember {
        mutableStateOf(false)
    }

    var movie by remember {
        mutableStateOf(MovieDoub())
    }

    LaunchedEffect(Unit) {

        viewModel.loadData("https://movie.douban.com/j/search_subjects?&type=movie&sort=recommend&tag=%E7%83%AD%E9%97%A8&page_limit=21&page_start=1")
    }
    //viewModel.loadData("http://cj.ffzyapi.com/api.php/provide/vod/from/ffm3u8?ac=videolist&wd=飞驰人生")

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = title) },
            navigationIcon = {
                if (showDetail) {
                    ShapedBackIcon(icon = Icons.Filled.ArrowBack,
                        size = 24.dp,
                        modifier = Modifier.clickable {
                            showDetail = !showDetail
                            title = "热门影视"
                        })
                }

            })
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (state.value) {
                is UiState.Initial -> {
                    Text(text = "初始化")
                }

                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Empty -> {
                    Text(text = "空")
                }

                is UiState.Error<*> -> {
                    Text(text = "错误")
                }

                is UiState.Success<*> -> {
                    val resp = (state.value as UiState.Success<*>).data.toString()

                    SharedTransitionLayout {
                        AnimatedContent(targetState = showDetail) { targetState ->
                            if (targetState) {
                                DetailScreen(
                                    onBack = {
                                        showDetail = false
                                        title = "热门影视"
                                    },
                                    movie = movie,
                                    animatedVisibilityScope = this@AnimatedContent,
                                )
                            } else {
                                MainScreen(
                                    resp = resp,
                                    onDetails = {
                                        showDetail = true
                                        title = it.title
                                        movie = it
                                    },
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            }

                        }

                    }

                }
            }
        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainScreen(
    resp: String,
    onDetails: (MovieDoub) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {

    val lazyGridState = rememberLazyGridState()
    val movieResp = Gson().fromJson(resp, MovieResp::class.java)
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = lazyGridState,
        modifier = modifier
            .sharedBounds(
                sharedContentState = rememberSharedContentState(key = "bounds"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        items(movieResp.subjects.size, key = { it.hashCode() }) {
            val item = movieResp.subjects[it]

            Card(
                modifier = modifier
                    .padding(8.dp)

                    .clickable {
                        onDetails(item)
                    },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                AsyncImage(
                    model = item.cover,
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "cover_" + item.cover),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = item.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "name_" + item.title),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }

            /*            MovieCard(
                            item = item,
                            modifier = Modifier
                                .padding(8.dp),
                            onSelect = {
                                //Toast.makeText()
                                //viewModel.loadData("https://movie.douban.com/subject/26656728/")
                            }
                        )*/
        }
    }

}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    onBack: () -> Unit,
    movie: MovieDoub,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(key = "bounds"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        Column {
            Row(modifier = modifier) {

                AsyncImage(
                    model = movie.cover,
                    contentDescription = "电影封面",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(240.dp)
                        .width(160.dp)
                        .padding(start = 16.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "cover_" + movie.cover),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .clip(MaterialTheme.shapes.medium),
                )


                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = movie.id,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.extraSmall
                            )
                            .sharedElement(
                                state = rememberSharedContentState(key = "name_" + movie.title),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .padding(6.dp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.rate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .padding(6.dp)

                    )
                }
            }

            VideoPlayer(
                mediaItems = listOf(
                    VideoPlayerMediaItem.NetworkMediaItem(
                        url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                        mediaMetadata = MediaMetadata.Builder().setTitle("ElephantsDream").build(),
                        mimeType = MimeTypes.APPLICATION_MP4,
                        /*drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                            .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
                            .build(),*/
                    )
                ),
                handleLifecycle = true,
                autoPlay = true,
                usePlayerController = true,
                enablePip = true,
                handleAudioFocus = true,
                controllerConfig = VideoPlayerControllerConfig(
                    showSpeedAndPitchOverlay = false,
                    showSubtitleButton = false,
                    showCurrentTimeAndTotalTime = true,
                    showBufferingProgress = false,
                    showForwardIncrementButton = true,
                    showBackwardIncrementButton = true,
                    showBackTrackButton = true,
                    showNextTrackButton = true,
                    showRepeatModeButton = true,
                    controllerShowTimeMilliSeconds = 5_000,
                    controllerAutoShow = true,
                    showFullScreenButton = true
                ),
                volume = 0.5f,  // volume 0.0f to 1.0f
                repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
                onCurrentTimeChanged = { // long type, current player time (millisec)
                    Log.e("CurrentTime", it.toString())
                },
                playerInstance = { // ExoPlayer instance (Experimental)
                    addAnalyticsListener(
                        object : AnalyticsListener {
                            // player logger
                        }
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }


/*        FloatingActionButton(
            onClick = {

            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 16.dp,
                    end = 16.dp
                )
        ) {
            Icon(
                imageVector = Icons.TwoTone.PlayArrow,
                contentDescription = "播放"
            )
        }*/
    }

}


@Preview
@Composable
fun MovieScreenPreview() {
    MovieScreen()
}

