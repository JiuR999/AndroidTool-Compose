package com.example.mycomposedemo.router


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    title: String,
    subTitle: String? = null,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (subTitle == null) {
                MediumTopAppBar(
                    title = {
                        Column {
                            Text(text = title)
                        }

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBack()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "返回")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            } else {
                LargeTopAppBar(
                    title = {
                        Column {
                            Text(text = title)
                            Text(
                                text = subTitle,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBack()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "返回")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }

        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun AppNavHostPreview() {

}

