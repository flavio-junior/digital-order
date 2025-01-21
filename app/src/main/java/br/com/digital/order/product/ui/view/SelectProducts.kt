package br.com.digital.order.product.ui.view

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
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.product.data.dto.ProductResponseDTO
import br.com.digital.order.product.ui.viewmodel.ProductViewModel
import br.com.digital.order.product.utils.ProductsUtils.NO_PRODUCTS_SELECTED
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Search
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.Tag
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_PRODUCTS
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectProducts(
    onDismiss: () -> Unit = {},
    onResult: (List<ProductResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: ProductViewModel = koinViewModel()
    var isLoading: Boolean by remember { mutableStateOf(value = false) }
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val products = remember { mutableStateListOf<ProductResponseDTO>() }
    var productsSelected = remember { mutableStateListOf<ProductResponseDTO>() }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    SelectObject(
        body = {
            Title(
                title = ADD_PRODUCTS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findProductByName(name = name)
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
            if (products.isNotEmpty()) {
                SelectProducts(
                    products = products,
                    productsSelected = productsSelected,
                    onResult = {
                        productsSelected = it.toMutableStateList()
                    }
                )
                if (productsSelected.isNotEmpty()) {
                    ListProductsAvailable(products = productsSelected)
                }
            }
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = {
                    if (productsSelected.isNotEmpty()) {
                        viewModel.resetProduct()
                        onResult(productsSelected)
                        onDismiss()
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_PRODUCTS_SELECTED)
                    }
                }
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = {
                    viewModel.resetProduct()
                    onDismiss()
                }
            )
        },
        onDismiss = {
            viewModel.resetProduct()
            onDismiss()
        }
    )
    ObserveNetworkStateHandlerFindProductByName(
        viewModel = viewModel,
        onError = {
            observer = it
            isLoading = it.first
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = { result ->
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            isLoading = false
            products.clear()
            result.forEach { product ->
                if (!products.contains(element = product)) {
                    products.add(element = product)
                }
            }
        }
    )
}

@Composable
private fun SelectProducts(
    products: List<ProductResponseDTO>,
    productsSelected: MutableList<ProductResponseDTO>,
    onResult: (List<ProductResponseDTO>) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
    ) {
        items(products) { product ->
            Tag(
                text = product.name,
                value = product,
                onCheck = { isChecked ->
                    if (isChecked) {
                        productsSelected.add(product)
                    } else {
                        productsSelected.remove(product)
                    }
                    onResult(productsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListProductsAvailable(
    products: List<ProductResponseDTO>
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
        items(products) { products ->
            Description(description = products.name)
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindProductByName(
    viewModel: ProductViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ProductResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ProductResponseDTO>> by remember { viewModel.findProductByName }
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
