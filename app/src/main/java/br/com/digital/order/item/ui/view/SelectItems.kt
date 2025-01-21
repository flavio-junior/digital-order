package br.com.digital.order.item.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.item.data.dto.ItemResponseDTO
import br.com.digital.order.item.ui.viewmodel.ItemViewModel
import br.com.digital.order.item.utils.ItemUtils.NO_ITEM_SELECTED
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Search
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.Tag
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_ITEMS
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectItems(
    onDismiss: () -> Unit = {},
    onResult: (List<ItemResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: ItemViewModel = koinViewModel()
    var isLoading: Boolean by remember { mutableStateOf(value = false) }
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val items = remember { mutableStateListOf<ItemResponseDTO>() }
    var itemsSelected = remember { mutableStateListOf<ItemResponseDTO>() }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    SelectObject(
        body = {
            Title(
                title = ADD_ITEMS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findItemByName(name = name)
                    isLoading = true
                }
            )
            if (isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (items.isNotEmpty()) {
                SelectItems(
                    items = items,
                    itemsSelected = itemsSelected,
                    onResult = {
                        itemsSelected = it.toMutableStateList()
                    }
                )
                if (itemsSelected.isNotEmpty()) {
                    ListItemsAvailable(item = itemsSelected)
                }
            }
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = {
                    if (itemsSelected.isNotEmpty()) {
                        viewModel.resetItem()
                        onResult(itemsSelected)
                        onDismiss()
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_ITEM_SELECTED)
                    }
                }
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = {
                    viewModel.resetItem()
                    onDismiss()
                }
            )
        },
        onDismiss = {
            viewModel.resetItem()
            onDismiss()
        }
    )
    ObserveNetworkStateHandlerFindItemByName(
        viewModel = viewModel,
        onError = {
            observer = it
            isLoading = it.first
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = { result ->
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            isLoading = false
            items.clear()
            result.forEach { item ->
                if (!items.contains(element = item)) {
                    items.add(element = item)
                }
            }
        }
    )
}

@Composable
private fun SelectItems(
    items: List<ItemResponseDTO>,
    itemsSelected: MutableList<ItemResponseDTO>,
    onResult: (List<ItemResponseDTO>) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
    ) {
        items(items) { item ->
            Tag(
                text = item.name,
                value = item,
                onCheck = { isChecked ->
                    if (isChecked) {
                        itemsSelected.add(item)
                    } else {
                        itemsSelected.remove(item)
                    }
                    onResult(itemsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListItemsAvailable(
    item: List<ItemResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(item) { items ->
            Description(description = items.name)
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindItemByName(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ItemResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ItemResponseDTO>> by remember { viewModel.findItemByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
