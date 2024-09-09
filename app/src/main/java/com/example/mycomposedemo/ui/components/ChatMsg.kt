package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.R

@Composable
fun MineMsg(
    text: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End,
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)

    ) {
        Card(
            shape = MaterialTheme.shapes.large,

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(6.dp)
            )
        }

/*        Icon(
            Icons.Outlined.Person, contentDescription = "",
            modifier = Modifier.size(48.dp),
        )*/
    }
}


@Composable
fun OtherMsg(
    text: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(top = 4.dp)
    ) {

        Box(Modifier
            .padding(4.dp)
            .border(
            BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceVariant),
            shape = CircleShape)) {
            Icon(
                painter = painterResource(id = R.drawable.gpt), contentDescription = "",
                modifier = Modifier.size(32.dp).padding(4.dp)
                )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
        )
/*        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(6.dp)
            )
        }*/


    }
}

@Preview
@Composable
fun MineMsgPreview() {
    Column(verticalArrangement = Arrangement.SpaceAround) {
        MineMsg("你在干嘛")
        MineMsg("吃饭了吗")

        OtherMsg(text = "刚吃")


    }
}

