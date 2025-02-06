package br.com.digital.order.order.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.order.data.vo.OrdersResponseVO
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.WEIGHT_SIZE
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersTabs(
    ordersResponseVO: OrdersResponseVO,
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ItemsOrder.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            contentColor = Color.Transparent,
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxWidth()
        ) {
            ItemsOrder.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Description(description = currentTab.text)
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = WEIGHT_SIZE)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                OrderTabMain(
                    index = it,
                    ordersResponseVO = ordersResponseVO.content ?: emptyList(),
                    onClickable = goToNextScreen
                )
            }
        }
    }
}
