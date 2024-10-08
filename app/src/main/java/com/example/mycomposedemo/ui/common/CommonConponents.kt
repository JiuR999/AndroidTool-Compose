package com.example.mycomposedemo.ui.common


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.LabelImportant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.mycomposedemo.R
import com.example.mycomposedemo.ui.util.Dimensions

@Composable
fun EmptyCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Dimensions.DefaultPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty),
            contentDescription = "数据为空"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "404 NOT FOUND !", style = MaterialTheme.typography.displayMedium)
    }
}

@Preview
@Composable
fun EmptyCardPreview() {
    EmptyCard()
}

@Composable
fun UserCard() {
    Surface {
        /*AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.jiur)
            .scale(Scale.FILL)
            .build(), contentDescription = "头像")*/
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.DefaultPadding)) {
                Image(
                    painter = painterResource(id = R.drawable.jiur), contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(64.dp)
                )

                Column {
                    Text(
                        text = "JIUR",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.LabelImportant,
                            contentDescription = "邮箱",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "云鹤工具箱",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Email,
                            contentDescription = "邮箱",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "2041357138@qq.com",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

            }
        }

    }
}

@Preview
@Composable
fun UserCardPreview() {
    UserCard()
}

