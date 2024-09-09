package com.example.mycomposedemo.ui.sample


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.lang.Math.ceil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CanvasSample() {
    val weekValue = listOf(3,6,9,7,5,9,8)
    val lastXDaysDate = getLastXDaysDate(weekValue.size)
    //每一行的高度
    val heightForRow = 25
    //总行数
    val countForRow = 5
    // 计算向上取整的结果
    val avg = ceil(weekValue.max().toDouble() / countForRow).toInt()
    //纵坐标最大值
    val maxValue = countForRow * avg
    //小圆圈半径
    val circleRadius = 5f
    val textMeasure = rememberTextMeasurer()
    //画布宽度
    val canvasWidth = LocalConfiguration.current.screenWidthDp - 8 * 2
    val averageWidth = (canvasWidth-35) / weekValue.size
    //上下间隔
    val spaceHeight = 20
    //画布高度
    val canvasHeight = heightForRow * countForRow + spaceHeight * 2
    val circleColor = MaterialTheme.colorScheme.tertiaryContainer
    Canvas(modifier = Modifier.size(canvasWidth.dp, canvasHeight.dp), contentDescription = "") {
        for (index in 0 .. countForRow) {
            val textWidth = textMeasure.measure(weekValue[index].toString())
            val lineX = textWidth.size.width + 35f
            val endX = size.width
            val startY = (index * heightForRow.toFloat() + 20).dp.toPx()
            val endY = startY

            drawText(textMeasure, text = (maxValue- avg * index).toString(), topLeft = Offset(8f,startY - textWidth.size.height/2))
            
            drawLine(
                Color(0xFFEEEEEE),
                start = Offset(x = lineX, y = startY),
                end = Offset(x = endX, y = endY),
                strokeWidth = 2.5f)
        }

        val perY = avg.toFloat() / heightForRow
        var lastPoint = Offset(-1f,-1f)
        for (index in weekValue.indices) {

            val x = (35 + index * averageWidth + averageWidth / 2).dp.toPx()
            val y = (spaceHeight + (maxValue - weekValue[index]) / perY).dp.toPx()
            Log.e("画圆圈","index:${index},x:${x},y:${y},pery:${perY}")
            val center = Offset(x, y)
            drawCircle(color = circleColor, circleRadius, center, style = Stroke(5f))
            val currentPoint = Offset(x,y)
            if (lastPoint.x > 0f && lastPoint.y >0f) {
                drawLine(circleColor, start = lastPoint, end = currentPoint, strokeWidth = 5f)
            }
            lastPoint = currentPoint
            //绘制横坐标文字
            val textLayoutResult = textMeasure.measure(lastXDaysDate[index])
            val textY = spaceHeight + countForRow * heightForRow
            drawText(textMeasure, lastXDaysDate[index], topLeft = Offset(x-textLayoutResult.size.width/2, textY.toFloat().dp.toPx()))
        }
        
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun getLastXDaysDate(range : Int) : List<String>{
    // 获取今天的日期
    val today = LocalDate.now()
    // 创建一个日期格式化器
    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    // 生成过去七天的日期
    return (range downTo 0).map { today.minusDays(it.toLong()).format(formatter) }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CanvasSamplePreview() {
    CanvasSample()
}

