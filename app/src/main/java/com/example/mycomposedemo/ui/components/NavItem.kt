package com.example.mycomposedemo.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Newspaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.ui.util.Shapes


@Composable
fun NavItem(icon : ImageVector,
            name : String,
            subName : String,
            backgroundColor:Color = MaterialTheme.colorScheme.surface,
            iconShape : Shape = MaterialTheme.shapes.extraLarge,
            elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp,
                pressedElevation = 8.dp),
            modifier: Modifier = Modifier) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement =Arrangement.Center) {
            ShapedBackIcon(icon = icon,
                shape = iconShape,
                size = 48.dp)

            Text(text = name,
                style = MaterialTheme.typography.titleMedium)

            Text(text = subName,
                style = MaterialTheme.typography.bodySmall)

        }
    }
}

@Preview
@Composable
fun NavItemPreview() {
    NavItem(Icons.TwoTone.Newspaper,"热搜", "三大平台热搜",
        iconShape = Shapes.Rounded4StarShape)
}
