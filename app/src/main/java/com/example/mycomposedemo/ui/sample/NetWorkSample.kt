package com.example.mycomposedemo.ui.sample


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposedemo.models.History
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance

//待封装
@Composable
fun NetWorkSample() {
    var datas by remember { mutableStateOf<History?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            val response = PearnoRetrofitInstance.api.getHistory()
            datas = response
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            errorMessage?.let {
                Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
            } ?: run {
                datas?.let {
                    //HistorySample(data = datas!!.data)
                } ?: run {
                    Text(text = "No users found", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun NetWorkSamplePreview() {
    NetWorkSample()
}
