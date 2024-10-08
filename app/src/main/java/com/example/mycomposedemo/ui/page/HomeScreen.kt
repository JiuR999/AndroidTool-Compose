package com.example.mycomposedemo.ui.page


import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DashboardCustomize
import androidx.compose.material.icons.twotone.DisplaySettings
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.mycomposedemo.R
import com.example.mycomposedemo.models.BottomItemData
import com.example.mycomposedemo.models.GroupModel
import com.example.mycomposedemo.repo.net.HitokotoRetrofitInstance
import com.example.mycomposedemo.repo.net.PearnoRetrofitInstance
import com.example.mycomposedemo.router.AppNavHost
import com.example.mycomposedemo.router.Screen
import com.example.mycomposedemo.ui.common.UserCard
import com.example.mycomposedemo.ui.components.Banner
import com.example.mycomposedemo.ui.components.NavItem
import com.example.mycomposedemo.ui.sample.AnswerCard
import com.example.mycomposedemo.ui.sample.ChatSample
import com.example.mycomposedemo.ui.theme.LocalWindow
import com.example.mycomposedemo.ui.util.Dimensions
import com.example.mycomposedemo.ui.util.Shapes
import com.example.mycomposedemo.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
) {

    val listState = rememberLazyListState()
    var hitokotoTitle by remember {
        mutableStateOf("随机一言")
    }
    val menuData = listOf(
        BottomItemData("首页", Screen.Home.router, Icons.TwoTone.Home),
        BottomItemData("工具", Screen.Tool.router, Icons.TwoTone.DashboardCustomize)
    )

    val context = LocalContext.current
    val images = listOf(
        R.drawable.two,
        R.drawable.three,
        R.drawable.jiur,
    )
    val pageState = rememberPagerState(pageCount = {
        menuData.size
    })

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val homeViewModel = HomeViewModel()
    var isHomeTopbarVisible by remember {
        mutableStateOf(true)
    }
    isHomeTopbarVisible =
        currentDestination?.hierarchy?.any { it.route == Screen.Home.router } == true
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //TODO 使用Provider
    if (drawerState.isOpen == true) {
        //onScroll(false)
    } else {
        //onScroll(true)
    }
    val title = "首页"
    var isNavBarVisible by remember {
        mutableStateOf(
            true
        )
    }
    val height = 120
    var lastScrollPosition by remember { mutableStateOf(0) }

    // 监听滚动状态
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .collect { (index, offset) ->
                // 检查是否向上滑动
                val currentScrollPosition = index * height + offset
                //title = offset.toString()
                if (currentScrollPosition > lastScrollPosition) {
                    //onScroll(false)
                    isNavBarVisible = false
                } else {
                    // onScroll(true)
                    isNavBarVisible = true
                }
                lastScrollPosition = currentScrollPosition
            }
    }

    //侧滑
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                UserCard()
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                NavigationDrawerItem(
                    label = { Text(text = "关于") },
                    selected = true,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    ),
                    icon = {
                        Icon(
                            imageVector = Icons.TwoTone.Info,
                            contentDescription = "关于我们"
                        )
                    },
                    onClick = {
                        Toast.makeText(context, "暂未实现", Toast.LENGTH_SHORT).show()
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "主题设置") },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    ),
                    icon = {
                        Icon(
                            imageVector = Icons.TwoTone.DisplaySettings,
                            contentDescription = "设置"
                        )
                    },
                    onClick = {
                        navController.navigate(Screen.SettingTheme.router)
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "设置") },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.primary
                    ),
                    icon = {
                        Icon(
                            imageVector = Icons.TwoTone.Settings,
                            contentDescription = "设置"
                        )
                    },
                    onClick = {
                        navController.navigate(Screen.Setting.router)
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                // ...other drawer items
            }
        }
    ) {

        Scaffold(
            topBar = {
                if (isHomeTopbarVisible) {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menu),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },

                        )
                }
            },
            bottomBar = {
                if (isHomeTopbarVisible) {
                    AnimatedVisibility(visible = isNavBarVisible) {
                        NavigationBar {
                            menuData.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = index == pageState.currentPage,
                                    onClick = {
                                        scope.launch {
                                            pageState.animateScrollToPage(index)
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null
                                        )
                                    })

                            }
                        }
                    }

                }
            },
            modifier = Modifier.fillMaxSize()
        ) {

                innerPadding ->
            val context = LocalContext.current
            NavHost(
                modifier = Modifier
                    .padding(if (isHomeTopbarVisible) innerPadding else PaddingValues(0.dp))
                    .fillMaxSize(),
                navController = navController,
                startDestination = Screen.Home.router
            ) {
                composable(Screen.Home.router) {
                    HorizontalPager(
                        state = pageState,
                        userScrollEnabled = false,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> HomeDetail(
                                listState,
                                images,
                                homeViewModel,
                                navController
                            )

                            1 -> ToolPage(onFunction = {
                                hitokotoTitle = it.title
                                navController.navigate(it.router)
                            })
                        }
                    }
                }

                composable(Screen.BrainTeasers.router) {
                    DialogPage(
                        navController = navController,
                        dialogTitle = "脑筋急转弯",
                        icon = ImageVector.vectorResource(id = R.drawable.question),
                        confirmButton = {
                        }) {
                        AnswerCard(context)
                    }
                }
                composable(Screen.SettingTheme.router) {
                    AppearancePreferences(
                        back = { navController.popBackStack() },
                        navToDarkMode = { /*TODO*/ },
                        window = LocalWindow.current
                    )
                }

                composable(Screen.Setting.router) {
                    SettingScreen(LocalWindow.current)
                }

                composable(Screen.XiaoHongShu.router) {
                    AppNavHost("小红书解析", onBack = {
                        navController.popBackStack()
                    }) {
                        XiaoHongShuPage()
                    }
                }

                dialog(Screen.CrazyThursday.router) {
                    TextDialogPage(
                        context = context,
                        title = "疯狂星期四",
                        navController = navController
                    ) {
                        PearnoRetrofitInstance.api.getKFC().text
                    }
                }

                dialog(Screen.BrainTeasers.router) {
                    DialogPage(
                        navController = navController,
                        dialogTitle = "脑筋急转弯",
                        icon = ImageVector.vectorResource(id = R.drawable.question),
                        confirmButton = {
                        }) {
                        AnswerCard(context)
                    }
                }

                buildDialogNavHost(context, navController)

                buildHitokoto(navController, context, hitokotoTitle)

                composable(Screen.Chat.router) {
                    ChatSample()
                }
                composable(Screen.LearnWorld.router) {
                    AppNavHost(title = "60s读世界", onBack = {
                        navController.popBackStack()
                    }) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://api.pearktrue.cn/api/60s/image/")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.loading),
                            contentDescription = "60s读世界新闻图片",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
                composable(Screen.Movie.router) {
                    AppNavHost(title = "热门推荐", onBack = {
                        navController.popBackStack()
                    }) {
                        MovieScreen()
                    }
                }

                composable(Screen.WallerPaper.router) {
                    PictrueCategoryScreen(onBack = {
                        navController.popBackStack()
                    })
                }

                composable(Screen.Hotnews.router) {
                    AppNavHost(title = "热搜", onBack = {
                        navController.popBackStack()
                    }) {
                        HotNewPage()
                    }
                }
                composable(Screen.History.router) {
                    val now = LocalDateTime.now()
                    val month = now.month.value.toString()
                    val day = now.dayOfMonth.toString()
                    AppNavHost(
                        title = "历史上的今天",
                        subTitle = now.year.toString() + "年" + month + "月" + day + "日",
                        onBack = {
                            navController.popBackStack()
                        }
                    ) {
                        HistoryScreen(
                            month = month,
                            day = day
                        )
                    }
                }
            }
        }
}
}
        private fun NavGraphBuilder.buildDialogNavHost(
            context: Context,
            navController: NavHostController,
        ) {
            dialog(Screen.Tiangou.router) {
                TextDialogPage(
                    context = context,
                    title = "舔狗日记",
                    navController = navController
                ) {
                    PearnoRetrofitInstance.api.getTiangou()
                }
            }

            dialog(Screen.Romantic.router) {
                TextDialogPage(
                    context = context,
                    title = "经典情话",
                    navController = navController
                ) {
                    PearnoRetrofitInstance.api.getRomatic()
                }
            }

            dialog(Screen.Rainbow.router) {
                TextDialogPage(context = context, title = "彩虹屁", navController = navController) {
                    PearnoRetrofitInstance.api.getRainbow()
                }
            }

            dialog(Screen.CrazyThursday.router) {
                TextDialogPage(
                    context = context,
                    title = "疯狂星期四",
                    navController = navController
                ) {
                    PearnoRetrofitInstance.api.getKFC().text
                }
            }
        }

        private fun NavGraphBuilder.buildHitokoto(
            navController: NavHostController,
            context: Context,
            title: String,
        ) {
            dialog(
                Screen.Hitokoto.router + "{c}",
                arguments = listOf(navArgument("c") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                val c = it.arguments?.getString("c")
                TextDialogPage(context = context, title = title, navController = navController) {
                    HitokotoRetrofitInstance.api.getHitokoto(c).hitokoto
                }
            }
        }


        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        private fun HomeDetail(
            listState: LazyListState,
            images: List<Int>,
            homeViewModel: HomeViewModel,
            navController: NavController,
        ) {
            val ps = rememberPagerState {
                images.size
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Column {
                        Banner(
                            images = images,
                            previewPercent = 0f,
                            ps,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .height(200.dp)
                        )

                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Row {
                                images.forEachIndexed { index, _ ->
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (ps.currentPage == index) MaterialTheme.colorScheme.primaryContainer
                                                else colorResource(id = R.color.back)
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.Center) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(82.dp)
                                    .padding(16.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .weight(1f)
                                    .background(color = colorResource(id = R.color.back))
                            ) {
                                Text(
                                    text = "输入您想要的功能",
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            Icon(
                                imageVector = Icons.TwoTone.Search, contentDescription = "搜素",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(36.dp)

                            )

                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }

                }

                item {
                    Column(modifier = Modifier.height(720.dp)) {
                        FunctionLayout(homeViewModel.functions, navController)
                    }
                }
            }
        }

        @Composable
        private fun FunctionLayout(
            functions: List<GroupModel>,
            navController: NavController,
        ) {
            functions.forEach { group ->
                Text(
                    text = group.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = Dimensions.DefaultPadding)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(group.datas.size) {
                        val function = group.datas[it]
                        NavItem(
                            icon = function.icon,
                            name = function.title,
                            subName = function.subTitle,
                            iconShape = Shapes.Rounded4StarShape,
                            modifier = Modifier
                                .padding(16.dp)
                                .width(160.dp)
                                .height(120.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .clickable {
                                    navController.navigate(function.router)
                                }
                        )
                    }
                }
            }
        }
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomePreview() {
    HomeScreen()
}

