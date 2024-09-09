package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkDownSample(markdownContent : String) {
    Surface {
        val t = """
        ## 在Go语言中，如果你想把浮点数(float)转换为字符串(string)，你可以使用`strconv`包中的`FormatFloat`函数。以下是一个示例：\n\n
        ```go\npackage main\n\nimport (\n\t\"fmt\"\n\t\"strconv\"\n)\n\nfunc main() {\n\t// 浮点数\n\tvar f float64 = 123.456\n\n\t// 转换成字符串\n\ts := strconv.FormatFloat(f, 'f', -1, 64)\n\n\t// 打印字符串\n\tfmt.Println(s)\n}\n```
        \n\n在这个示例中，`strconv.FormatFloat`函数的参数解释如下：\n- `f`：要转换的浮点数。\n- `'f'`：表示输出为定点数形式，默认为小数形式。\n- `-1`：表示让系统自动确定小数部分的位数。如果是非负整数，则输出的小数点后位数固定。\n- `64`：表示浮点数是64位的。\n\n如果你想控制小数点后的位数，你可以改变第三个参数。例如，如果你想保留两位小数，你可以这样写：\n\n```go\ns := strconv.FormatFloat(f, 'f', 2, 64)\n```
    """.trimIndent()

/*        val markdownContent = """
# Sample
* Markdown  
* [Link](https://example.com)  
![Image](https://example.com/img.png)  
<a href="https://www.google.com/">Google</a>  
""".trimIndent()*/
        MarkdownText(
            modifier = Modifier.padding(8.dp),
            markdown = markdownContent,
            style = MaterialTheme.typography.bodyMedium,

            )
    }
}

@Preview
@Composable
fun MarkDownSamplePreview() {
    MarkDownSample("哈哈哈")
}

