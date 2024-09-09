package com.example.mycomposedemo.ui.page


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.R

@Composable
fun MovieDetail(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {

        Image(
            painter = painterResource(id = R.drawable.jiur),
            contentDescription = null,
            modifier = Modifier
                .height(160.dp)
                .width(90.dp)
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

@Preview
@Composable
fun MovieDetailPreview() {
    MovieDetail(modifier = Modifier.padding(8.dp))
}

