package com.example.mycomposedemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycomposedemo.ui.sample.UiState
import com.example.mycomposedemo.utils.OkHttpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData(url: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val data = withContext(Dispatchers.IO) {
                //"https://api.pearktrue.cn/api/xfai/?message=$que"
                fetchData(url)
            }
            _uiState.value = if (data == "error") {
                UiState.Error("error")
            } else {
                UiState.Success(data)
            }
        }
    }

    private suspend fun fetchData(url: String): String = withContext(Dispatchers.IO){
        return@withContext OkHttpUtils.get(url)
    }
}

