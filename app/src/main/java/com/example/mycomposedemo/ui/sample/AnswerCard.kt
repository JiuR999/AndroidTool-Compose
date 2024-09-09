package com.example.mycomposedemo.ui.sample


import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.repo.models.pearno.BrainTeasersModel
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.utils.TextUtils

@Composable
fun AnswerCard(context: Context) {
    var count by remember {
        mutableIntStateOf(0)
    }
    var brainTeasersModel by remember {
        mutableStateOf(BrainTeasersModel())
    }
    var visible by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    val buttonOffsetX by animateDpAsState(
        targetValue = if (visible) 80.dp else 0.dp,
        label = "按钮的水平位移动画"
    ) // 按钮的水平位移动画
    val textAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "文本的透明度渐变动画"
    ) // 文本的透明度渐变动画
    LaunchedEffect(count) {
        isLoading = true
        brainTeasersModel = PearnoRetrofitInstance.api.getBrainteasers()
        isLoading = false
    }
    Surface {
        Column {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Text(text = brainTeasersModel.data.question)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.DefaultPadding)
            ) {
                AnimatedVisibility(visible = visible) {
                    Text(
                        text = brainTeasersModel.data.answer,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .alpha(textAlpha)
                    )
                }

                Button(onClick = { visible = !visible }) {
                    AnimatedContent(targetState = visible) {
                        if (it) {
                            Text(text = "隐藏答案")
                        } else {
                            Text(text = "显示答案")
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { count++ }) {
                    Text(text = "刷新")
                }

                TextButton(onClick = {
                    TextUtils.CopyToClipboard(context, brainTeasersModel.data.question)
                    Toast.makeText(context, "成功复制到剪贴板", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "复制问题")
                }
            }
        }
    }

}

@Preview
@Composable
fun AnswerCardPreview() {
    AnswerCard(LocalContext.current)
}

