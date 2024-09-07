package com.example.mycomposedemo.ui.page


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposedemo.models.NavigationModel
import com.example.mycomposedemo.ui.components.ExpandableCard
import com.example.mycomposedemo.viewmodel.ToolViewModel

@Composable
fun ToolPage(onFunction: (NavigationModel) -> Unit) {
    val viewModel = ToolViewModel()
    Column {
        ExpandableCard(functions = viewModel.textTool,
            onFunction = {
                onFunction(it)
            })

        ExpandableCard(functions = viewModel.textTool,
            onFunction = {
                onFunction(it)
            })

        ExpandableCard(functions = viewModel.textTool,
            onFunction = {
                onFunction(it)
            })
    }

}

@Preview
@Composable
fun ToolPagePreview() {
    ToolPage(onFunction = {

    })
}

