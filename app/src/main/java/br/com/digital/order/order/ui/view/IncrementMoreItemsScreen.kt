package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import br.com.digital.order.dashboard.ui.view.BodyObject
import br.com.digital.order.dashboard.ui.view.BodyRequestsOrder
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.order.ui.viewmodel.ResetOrder
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.IsErrorMessage
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.OrdersUtils.NUMBER_EQUALS_ZERO
import br.com.digital.order.utils.StringsUtils.ADD_ITEMS
import br.com.digital.order.utils.StringsUtils.NOT_BLANK_OR_EMPTY
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncrementMoreItemsScreen(
    orderId: Long? = null,
    goToBack: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: OrderViewModel = koinViewModel()
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
            title = R.string.add_more_items,
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
            BodyRequestsOrder(
                objectsToSave = objectsToSave,
                contentObjects = contentObjects,
                verifyObjects = verifyObjects,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            LoadingButton(
                label = ADD_ITEMS,
                onClick = {
                    if (objectsToSave.isEmpty()) {
                        observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)

                    } else {
                        verifyObjects = objectsToSave.any { it.quantity == 0 }
                        if (verifyObjects) {
                            observer =
                                Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
                        } else {
                            verifyObjects = objectsToSave.any { it.quantity == 0 }
                            if (verifyObjects) {
                                observer =
                                    Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
                            } else {
                                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                                viewModel.incrementMoreObjectsOrder(
                                    orderId = orderId ?: 0,
                                    incrementObjects = objectsToSave.toList()
                                )
                            }
                        }
                    }
                },
                isEnabled = observer.first
            )
            IsErrorMessage(isError = observer.second, message = observer.third)
        }
    }
    ObserveStateIncrementMoreObjectsOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccess = {
            goToNextScreen(RouteApp.Dashboard.item)
        }
    )
}

@Composable
private fun ObserveStateIncrementMoreObjectsOrder(
    viewModel: OrderViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.incrementMoreObjectsOrder }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.INCREMENT_MORE_OBJECTS_ORDER)
            onSuccess()
        }
    )
}
