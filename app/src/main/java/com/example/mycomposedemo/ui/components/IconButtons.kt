package com.kiylx.compose_lib.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.R

/**
 * 长按会展示icon的button
 */
@Composable
fun PressIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(
        onClick = onClick, modifier = modifier,
        interactionSource = interactionSource
    ) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    icon()
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        text()
    }
}

@Composable
fun PasteFromClipBoardButton(onPaste: (String) -> Unit = {}) {
    val clipboardManager = LocalClipboardManager.current
    PasteButton(onClick = {
        clipboardManager.getText()?.let { onPaste(it.toString()) }
    })
}

@Composable
fun PasteButton(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) {
        Icon(
            Icons.Outlined.ContentPaste,
            stringResource(R.string.paste)
        )
    }
}

@Composable
fun AddButton(onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(
        onClick = onClick, enabled = enabled
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(
                R.string.add
            )
        )
    }
}

@Composable
fun ClearButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Outlined.Cancel,
            contentDescription = stringResource(id = R.string.clear),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(modifier = Modifier, onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.outline_arrow_back_24),
            contentDescription = stringResource(R.string.back),
        )
    }
}

/**
 * @param interval 限制点击时间间隔
 */

fun Modifier.limitClick(interval: Long): Modifier {
    var lastClickTime = 0L
    return this.then(Modifier.pointerInput(key1 = Unit, block = {
        this.awaitEachGesture {
            val firstClick = awaitFirstDown(pass = PointerEventPass.Initial)
            if (System.currentTimeMillis() - lastClickTime < interval) {//周期内重复点击，直接拦截消费
                firstClick.consume()
            } else {
                lastClickTime = System.currentTimeMillis()
            }
        }
    }))
}