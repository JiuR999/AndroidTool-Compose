package com.example.mycomposedemo.ui.sample


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShareSample() {
    var showDetail by remember {
        mutableStateOf(
            true
        )
    }

    SharedTransitionLayout {
        AnimatedContent(targetState = showDetail) { targetState ->

            if (targetState) {
                DetailScreen(
                    onBack = { showDetail = false },
                    animatedVisibilityScope = this@AnimatedContent,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                MainScreen(
                    onDetails = { showDetail = true },
                    animatedVisibilityScope = this@AnimatedContent,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainScreen(
    onDetails: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
    ) {

        Image(
            painter = painterResource(id = R.drawable.jiur), contentDescription = null,
            modifier = Modifier
                .height(160.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "cover"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clickable { onDetails() }
        )

        Text(
            text = "勇者之心",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    onBack: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {


        Row(modifier = modifier) {

            Image(
                painter = painterResource(id = R.drawable.jiur),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .width(90.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "cover"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .clip(MaterialTheme.shapes.medium),

                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "勇者之心",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.extraSmall
                        )
                        .padding(6.dp)

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "9.8",
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

    }
}

@Preview
@Composable
fun ShareSamplePreview() {
    ShareSample()
}

