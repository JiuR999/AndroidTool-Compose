package com.example.mycomposedemo.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material.icons.twotone.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.size.Dimension
import com.example.mycomposedemo.models.NavigationModel
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableCard(functions : List<NavigationModel>,
                   onFunction: (NavigationModel) -> Unit) {
    var visible by remember {
        mutableStateOf(true)
    }
    val rotationAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val typography = MaterialTheme.typography
    Card(shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = Modifier.padding(Dimensions.DefaultPadding)) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                ShapedBackIcon(
                    icon = Icons.TwoTone.TextFields,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Column(modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)) {
                    Text(text = "文本工具",
                        style = typography.titleMedium)
                    Text(text = "Text Tool",
                        style = typography.bodySmall)
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            if (visible) rotationAnim.animateTo(0f)
                            else rotationAnim.animateTo(-90f)
                        }
                        visible = !visible
                    },
                ) {
                    Icon(
                        modifier = Modifier.graphicsLayer {
                            rotationZ = rotationAnim.value
                        },
                        imageVector = Icons.TwoTone.KeyboardArrowDown, contentDescription = ""
                    )
                }
            }

            AnimatedVisibility(visible = visible) {
                FlowRow {
                    repeat(functions.size) {
                        val item = functions[it]
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .padding(8.dp)
                                .shadow(2.dp, MaterialTheme.shapes.extraLarge)
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    MaterialTheme.shapes.extraLarge
                                )
                                .clickable {
                                    onFunction(item)
                                }
                        ) {

                            Text(
                                text = item.title,
                                modifier = Modifier.padding(4.dp).align(Alignment.Center),
                                style = typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpandSamplePreview() {
    val viewModel = HomeViewModel()
    ExpandableCard(viewModel.functions[1].datas,
        onFunction = {

        })
}

