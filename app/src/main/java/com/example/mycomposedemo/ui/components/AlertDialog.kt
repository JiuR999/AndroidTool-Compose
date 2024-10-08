package com.example.mycomposedemo.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.utils.TextUtils

@Composable
fun AlertTextDialog(
    context: Context,
    dialogTitle: String,
    dialogText: String,
    actionText: String = "复制",
    refreshable: Boolean = false,
    icon: ImageVector,
    onDismissRequest: () -> Unit,
    onRefresh: (() -> Unit)? = null,
) {
    AlertContentDialog(
        onDismissRequest = onDismissRequest,
        dialogTitle = dialogTitle,
        icon = icon,
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (refreshable) {
                    TextButton(
                        onClick = {
                            onRefresh?.let {
                                onRefresh()
                            }
                        }
                    ) {
                        Text("刷新")
                    }
                }
                TextButton(
                    onClick = {
                        TextUtils.CopyToClipboard(context, dialogText)
                        Toast.makeText(context, "已成功复制到剪切板", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(actionText)
                }
            }
        }
    ) {
        Text(text = dialogText)
    }
}

/**
 * 带标题和图标的对话框
 * @param onDismissRequest 关闭对话框的回调
 * @param dialogTitle 对话框标题
 * @param icon 对话框图标
 *  @param content 对话框内容
 *  @param confirmButton 对话框确认按钮
 *  @param content 对话框内容
 *  @param dismissButton 对话框取消按钮
 *  @param onDismissRequest 关闭对话框的回调
 *  @param onConfirmation 点击确认按钮的回调
 */
@Composable
fun AlertContentDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String? = null,
    icon: ImageVector? = null,
    confirmButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        icon = {
            icon?.let {
                Icon(icon, contentDescription = "Example Icon")
            }
        },
        title = {
            dialogTitle?.let {
                Text(text = dialogTitle)
            }
        },
        text = {
            content()
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            confirmButton()
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("取消")
            }
        }
    )
}