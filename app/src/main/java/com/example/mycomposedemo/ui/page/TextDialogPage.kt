package com.example.mycomposedemo.ui.page


import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Article
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.mycomposedemo.ui.components.AlertContentDialog
import com.example.mycomposedemo.ui.components.AlertTextDialog
import com.example.mycomposedemo.utils.TextUtils

@Composable
fun DialogPage(
    navController: NavController,
    dialogTitle: String,
    icon: ImageVector,
    confirmButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertContentDialog(
        onDismissRequest = { navController.popBackStack() },
        dialogTitle = dialogTitle,
        icon = icon,
        confirmButton = confirmButton,

    ) {
        content()
    }
}

@Composable
fun TextDialogPage(
    context: Context,
    title: String,
    navController: NavController,
    getText: suspend () -> String,
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    var text by remember {
        mutableStateOf("")
    }
    var count by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(count) {
        text = getText()
        isLoading = false
    }
        AlertTextDialog(
            onDismissRequest = { navController.popBackStack() },
            context = context,
            onRefresh = {
                count++
                isLoading = true
            },
            dialogTitle = title,
            dialogText = if (isLoading) "Loading..." else text,
            actionText = "复制",
            refreshable = true,
            icon = Icons.TwoTone.Article
        )
}

@Preview
@Composable
fun TextPagePreview() {
    //TextPage()
}

