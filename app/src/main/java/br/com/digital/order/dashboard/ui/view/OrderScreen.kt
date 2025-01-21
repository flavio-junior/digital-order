package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.common.dto.TypeItem
import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.dashboard.ui.viewmodel.DashboardViewModel
import br.com.digital.order.food.ui.view.SelectFoods
import br.com.digital.order.item.ui.view.SelectItems
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.product.ui.view.SelectProducts
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.IsErrorMessage
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.OrdersUtils.NUMBER_EQUALS_ZERO
import br.com.digital.order.utils.StringsUtils.ADD_FOOD
import br.com.digital.order.utils.StringsUtils.ADD_ITEM
import br.com.digital.order.utils.StringsUtils.ADD_PRODUCT
import br.com.digital.order.utils.StringsUtils.CREATE_NEW_ORDER
import br.com.digital.order.utils.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.order.utils.StringsUtils.SELECTED_ITEMS
import org.koin.androidx.compose.koinViewModel

data class BodyObject(
    val name: String,
    val quantity: Int
)

@Composable
fun OrderScreen(
    goToBack: () -> Unit = { },
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: DashboardViewModel = koinViewModel()
    var addNewProduct: Boolean by remember { mutableStateOf(value = false) }
    var addNewFood: Boolean by remember { mutableStateOf(value = false) }
    var addNewItem: Boolean by remember { mutableStateOf(value = false) }
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    val contentObjects = remember { mutableStateListOf<BodyObject>() }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.order_to_pickup,
            goToBack = goToBack
        )
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            OptionButton(
                icon = R.drawable.add,
                label = ADD_PRODUCT,
                onClick = {
                    addNewProduct = true
                }
            )
            OptionButton(
                icon = R.drawable.add,
                label = ADD_FOOD,
                onClick = {
                    addNewFood = true
                }
            )
            OptionButton(
                icon = R.drawable.add,
                label = ADD_ITEM,
                onClick = {
                    addNewItem = true
                }
            )
            Description(description = SELECTED_ITEMS)
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier.horizontalScroll(state = rememberScrollState())
            ) {
                objectsToSave.forEach { objectResult ->
                    val quantity = contentObjects.find { it.name == objectResult.name }?.quantity
                        ?: NUMBER_ZERO
                    CardObjectSelect(
                        objectRequestDTO = objectResult,
                        verifyObject = verifyObjects,
                        quantity = quantity,
                        onQuantityChange = {
                            objectResult.quantity = it
                        },
                        onItemSelected = {
                            if (objectsToSave.contains(element = it)) {
                                objectsToSave.remove(element = it)
                            }
                        }
                    )
                }
            }
            LoadingButton(
                label = CREATE_NEW_ORDER,
                onClick = {
                    if (objectsToSave.isEmpty()) {
                        observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                    } else {
                        verifyObjects = objectsToSave.any { it.quantity == 0 }
                        if (verifyObjects) {
                            observer =
                                Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
                        } else {
                            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                            viewModel.createOrder(
                                order = OrderRequestDTO(
                                    type = TypeOrder.ORDER,
                                    objects = objectsToSave.toList()
                                )
                            )
                        }
                    }
                },
                isEnabled = observer.first
            )
            IsErrorMessage(isError = observer.second, message = observer.third)
        }
    }
    if (addNewProduct) {
        SelectProducts(
            onDismiss = {
                addNewProduct = false
            },
            onResult = {
                it.forEach { product ->
                    val productSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        quantity = 0,
                        type = TypeItem.PRODUCT
                    )
                    if (!objectsToSave.contains(element = productSelected)) {
                        objectsToSave.add(productSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    if (addNewFood) {
        SelectFoods(
            onDismiss = {
                addNewFood = false
            },
            onResult = {
                it.forEach { product ->
                    val foodSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        quantity = 0,
                        type = TypeItem.FOOD
                    )
                    if (!objectsToSave.contains(element = foodSelected)) {
                        objectsToSave.add(foodSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    if (addNewItem) {
        SelectItems(
            onDismiss = {
                addNewItem = false
            },
            onResult = {
                it.forEach { item ->
                    val itemSelected = ObjectRequestDTO(
                        name = item.name,
                        identifier = item.id,
                        quantity = 0,
                        type = TypeItem.ITEM
                    )
                    if (!objectsToSave.contains(element = itemSelected)) {
                        objectsToSave.add(itemSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    ObserveNetworkStateHandlerCreateNewOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = goToBack
    )
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewOrder(
    viewModel: DashboardViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createOrder }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it.orEmpty()))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder()
            onSuccessful()
        }
    )
}
