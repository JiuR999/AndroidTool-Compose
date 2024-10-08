package com.example.mycomposedemo.ui.sample


import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFold
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * <a href="https://m3.material.io/components/tabs/overview" class="external" target="_blank">Material Design tabs</a>
 *
 * Material Design scrollable tabs.
 *
 * For primary indicator tabs, use [PrimaryScrollableTabRow]. For secondary indicator tabs, use
 * [SecondaryScrollableTabRow].
 *
 * When a set of tabs cannot fit on screen, use scrollable tabs. Scrollable tabs can use longer text
 * labels and a larger number of tabs. They are best used for browsing on touch interfaces.
 *
 * A ScrollableTabRow contains a row of [Tab]s, and displays an indicator underneath the currently
 * selected tab. A ScrollableTabRow places its tabs offset from the starting edge, and allows
 * scrolling to tabs that are placed off screen. For a fixed tab row that does not allow
 * scrolling, and evenly places its tabs, see [TabRow].
 *
 * @param selectedTabIndex the index of the currently selected tab
 * @param modifier the [Modifier] to be applied to this tab row
 * @param containerColor the color used for the background of this tab row. Use [Color.Transparent]
 * to have no color.
 * @param contentColor the preferred color for content inside this tab row. Defaults to either the
 * matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param edgePadding the padding between the starting and ending edge of the scrollable tab row,
 * and the tabs inside the row. This padding helps inform the user that this tab row can be
 * scrolled, unlike a [TabRow].
 * @param indicator the indicator that represents which tab is currently selected. By default this
 * will be a [TabRowDefaults.SecondaryIndicator], using a [TabRowDefaults.tabIndicatorOffset] modifier to
 * animate its position. Note that this indicator will be forced to fill up the entire tab row, so
 * you should use [TabRowDefaults.tabIndicatorOffset] or similar to animate the actual drawn
 * indicator inside this space, and provide an offset from the start.
 * @param divider the divider displayed at the bottom of the tab row. This provides a layer of
 * separation between the tab row and the content displayed underneath.
 * @param tabs the tabs inside this tab row. Typically this will be multiple [Tab]s. Each element
 * inside this lambda will be measured and placed evenly across the row, each taking up equal space.
 */
@Composable
fun CustomScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    edgePadding: Dp = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
    divider: @Composable () -> Unit = @Composable {
        HorizontalDivider()
    },
    tabs: @Composable () -> Unit
) {
    CustomScrollableTabRowImp(
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        edgePadding = edgePadding,
        divider = divider,
        tabs = tabs,
        scrollState = rememberScrollState()
    )
}

@Composable
private fun CustomScrollableTabRowImp(
    selectedTabIndex: Int,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    edgePadding: Dp = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
    divider: @Composable () -> Unit = @Composable {
        HorizontalDivider()
    },
    tabs: @Composable () -> Unit,
    scrollState: ScrollState,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            contentColor = contentColor,
            containerColor = containerColor
        )
    ) {
        val ScrollableTabRowMinimumTabWidth = 90.dp
        val coroutineScope = rememberCoroutineScope()
        val scrollableTabData = remember(scrollState, coroutineScope) {
            ScrollableTabData(
                scrollState = scrollState,
                coroutineScope = coroutineScope
            )
        }
        SubcomposeLayout(
            Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val minTabWidth = ScrollableTabRowMinimumTabWidth.roundToPx()
            val padding = edgePadding.roundToPx()

            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)

            val tabRowWidth = measureTabRow(tabMeasurables, constraints.maxWidth)
            val tabCount = tabMeasurables.size
            var tabWidth = 0
            if (tabCount > 0) {
                tabWidth = (tabRowWidth / tabCount)
            }

            val tabRowHeight = tabMeasurables.fold(initial = 0) { max, curr ->
                maxOf(curr.maxIntrinsicHeight(tabWidth), max)
            }
            //val tabPlaceables = mutableListOf<Placeable>()
            val tabContentWidths = mutableListOf<Dp>()
            val tabPlaceables = tabMeasurables.map {
                it.measure(
                    constraints.copy(
                        minWidth = tabWidth,
                        maxWidth = tabWidth,
                        minHeight = tabRowHeight,
                        maxHeight = tabRowHeight,
                    )
                )
            }

            // Place the tabs
            val tabPositions = List(tabCount) { index ->
                TabPosition(tabWidth.toDp() * index, tabWidth.toDp())
            }
            val layoutWidth = tabPlaceables.fastFold(initial = padding * 2) { curr, measurable ->
                curr + measurable.width
            }

            // Position the children.
            layout(layoutWidth, tabRowHeight) {
                val left = padding
                subcompose(TabSlots.Indicator) {
                    indicator(tabPositions)
                }.fastForEach {
                    it.measure(Constraints.fixed(layoutWidth, tabRowHeight)).placeRelative(left, 0)
                }

                //绘制tab
                tabPlaceables.fastForEachIndexed { index, placeable ->
                    placeable.placeRelative(left + index * tabWidth, 0)
                }

                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    edgeOffset = padding,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}

fun measureTabRow(
    measurables: List<Measurable>,
    minWidth: Int
): Int {
    // 依次测量标签页宽度并求和
    val widths = measurables.map {
        it.minIntrinsicWidth(Int.MAX_VALUE)
    }
    var width = widths.max() * measurables.size
    measurables.forEach {
        width += it.minIntrinsicWidth(Int.MAX_VALUE)
    }

    // 如果标签较多,可以取一个较小值作为最大标签宽度,防止过宽
    return minOf(width, minWidth)
}

@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp) {
    val right: Dp get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width)"
    }
}

// Tab specifications
private val SmallTabHeight = 48.0.dp
private val LargeTabHeight = 72.dp

// Tab transition specifications
private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

// The horizontal padding on the left and right of text
internal val HorizontalTextPadding = 16.dp

// Distance from the top of the indicator to the text baseline when there is one line of text and an
// icon
private val SingleLineTextBaselineWithIcon = 14.dp
// Distance from the top of the indicator to the last text baseline when there are two lines of text
// and an icon
private val DoubleLineTextBaselineWithIcon = 6.dp
// Distance from the first text baseline to the bottom of the icon in a combined tab
private val IconDistanceFromBaseline = 20.sp
// Distance from the end of the leading icon to the start of the text
private val TextDistanceFromLeadingIcon = 8.dp

/**
 * Secondary indicator, which will be positioned at the bottom of the [TabRow], on top of the
 * divider.
 *
 * @param modifier modifier for the indicator's layout
 * @param height height of the indicator
 * @param color color of the indicator
 */
@Composable
fun SecondaryIndicator(
    modifier: Modifier = Modifier,
    height: Dp = 3.0.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color)
    )
}

fun Modifier.tabIndicatorOffset(
    currentTabPosition: androidx.compose.material3.TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

fun Modifier.tabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

private enum class TabSlots {
    Tabs,
    Divider,
    Indicator
}

private class ScrollableTabData(
    private val scrollState: ScrollState,
    private val coroutineScope: CoroutineScope,
) {
    private var selectedTab: Int? = null

    fun onLaidOut(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>,
        selectedTab: Int,
    ) {
        // Animate if the new tab is different from the old tab, or this is called for the first
        // time (i.e selectedTab is `null`).
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                // Scrolls to the tab with [tabPosition], trying to place it in the center of the
                // screen or as close to the center as possible.
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, tabPositions)
                if (scrollState.value != calculatedOffset) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            calculatedOffset,
                            animationSpec = ScrollableTabRowScrollSpec
                        )
                    }
                }
            }
        }
    }

    /**
     * @return the offset required to horizontally center the tab inside this TabRow.
     * If the tab is at the start / end, and there is not enough space to fully centre the tab, this
     * will just clamp to the min / max position given the max width.
     */
    private fun TabPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>,
    ): Int = with(density) {
        val totalTabRowWidth = tabPositions.last().right.roundToPx() + edgeOffset
        val visibleWidth = totalTabRowWidth - scrollState.maxValue
        val tabOffset = left.roundToPx()
        val scrollerCenter = visibleWidth / 2
        val tabWidth = width.roundToPx()
        val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
        // How much space we have to scroll. If the visible width is <= to the total width, then
        // we have no space to scroll as everything is always visible.
        val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
        return centeredTabOffset.coerceIn(0, availableSpace)
    }
}

/**
 * [AnimationSpec] used when scrolling to a tab that is not fully visible.
 */
private val ScrollableTabRowScrollSpec: AnimationSpec<Float> = tween(
    durationMillis = 250,
    easing = FastOutSlowInEasing
)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CustomTabPreview() {
    val tabTitles = listOf("微博", "百度", "知乎")
    val pageState = rememberPagerState {
        tabTitles.size
    }
    CustomScrollableTabRow(modifier = Modifier.fillMaxSize(),selectedTabIndex = 0, tabs = { repeat(10) {
        Text(text = "${it}", textAlign = TextAlign.Center,)
    } }, indicator = {
        Box(
            Modifier
                .fillMaxWidth()
                .tabIndicatorOffset(it[pageState.currentPage])
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    })
}

