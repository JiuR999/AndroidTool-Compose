package com.example.mycomposedemo.ui.page


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycomposedemo.repo.models.wallerpaper.Category
import com.example.mycomposedemo.repo.models.wallerpaper.WallerCategory
import com.example.mycomposedemo.repo.net.PictureRetrofitInstance
import com.example.mycomposedemo.ui.components.PictrueCategoryCard
import com.example.mycomposedemo.ui.page.pictrue.WallerDetailScreen
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.ui.util.Shapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictrueCategoryScreen(onBack: () -> Unit ) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var title by remember {
        mutableStateOf("壁纸大全")
    }
    var datas by remember {
        mutableStateOf<WallerCategory?>(null)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    var snackbarVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var categoryId : String? = null
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        try {
            datas = PictureRetrofitInstance.api.getLightwpCategory()
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }

    Scaffold(topBar = {
        MediumTopAppBar(title = {
            Text(text = title)
        },
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = {
                    if (title == "壁纸大全") {
                        onBack()
                    } else {
                        title = "壁纸大全"
                        navController.popBackStack()
                    }
                }) {
                    Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Back")
                }
            }
        )
    },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        }) {
        Surface(modifier = Modifier
            .padding(it)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
            ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                errorMessage?.let {
                    Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                } ?: run {
                    datas?.res?.category?.let {
                        val datas = it
                        NavHost(
                            navController = navController,
                            startDestination = "category"
                        ) {
                            composable("category") {
                                buildCategoryScreen(datas, navController, onClick = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("点击了${datas[it].id}")
                                    }
                                    categoryId = datas[it].id

                                    navController.navigate("detail/${categoryId}")
                                    title = datas[it].name
                                })
                            }

                            composable("detail/{id}") {
                                val id = it.arguments?.getString("id")
                                id?.let {
                                    WallerDetailScreen(id = id)
                                }
                            }
                        }

                    } ?: run {
                        //数据为空
                    }
                }
            }


            if (snackbarVisible) {
                Snackbar {
                    Text(text = categoryId!!)
                }
            }
        }
    }
}

@Composable
private fun buildCategoryScreen(
    datas: List<Category>,
    navController: NavHostController,
    onClick: (index : Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(Dimensions.DefaultPadding)
    ) {
        items(datas.size) { index ->
            val category = datas[index]
            PictrueCategoryCard(category = category,
                click = {
                    onClick(index)
                })
        }
    }
}

@Preview
@Composable
fun PictrueCategoryScreenPreview() {
    PictrueCategoryScreen(onBack = {

    })
}

