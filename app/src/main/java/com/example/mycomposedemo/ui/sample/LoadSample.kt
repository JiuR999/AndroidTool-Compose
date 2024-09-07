package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposedemo.viewmodel.UiViewModel

sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    object Empty : UiState()

    data class Success<T>(val data : T) : UiState()
    data class Error<String>(val msg : String) : UiState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadSample(viewModel: UiViewModel = UiViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scro = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
LaunchedEffect(Unit) {
    viewModel.loadData("Go主要优点")
}
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(title = {
            Column {
                Text(text = "UiState",
                    style = MaterialTheme.typography.titleLarge)
                Text(text = "Test",
                    style = MaterialTheme.typography.titleMedium)
            }
        },
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior())
    }) {
        innerPadding->
        Box(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(scro)
            .fillMaxSize()) {
            when(uiState) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Empty -> Text(text = "kong")
                is UiState.Error<*> -> Text(text = "错误")
                is UiState.Initial -> Text(text = "初始化")
                is UiState.Success<*> -> Text(text = (uiState as UiState.Success<*>).data.toString())
            }
        }
    }
}

@Preview
@Composable
fun LoadSamplePreview() {
    LoadSample()
}

