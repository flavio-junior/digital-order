package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.dashboard.domain.others.Action
import br.com.digital.order.navigation.RouteApp
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.order.ui.viewmodel.ResetOrder
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import br.com.digital.order.utils.StringsUtils.DELETE_ITEM
import br.com.digital.order.utils.StringsUtils.DELETE_OBJECT
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsObjectScreen(
    orderId: Long? = null,
    objectResponseVO: ObjectResponseVO? = null,
    goToBack: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.details_order,
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
            Description(description = objectResponseVO.toString())
            Description(description = orderId.toString())
            DeleteObject(
                orderId = orderId ?: 0,
                objectId = objectResponseVO?.id ?: 0,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccess = {
                    goToNextScreen(RouteApp.Dashboard.item)
                }
            )
        }
    }
}

@Composable
fun DeleteObject(
    orderId: Long,
    objectId: Long,
    onSuccess: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: OrderViewModel = koinViewModel()
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    LoadingButton(
        label = DELETE_ITEM,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first
    )
    if (openDialog) {
        RemoveObject(
            onDismiss = {
                openDialog = false
            },
            onItemSelected = {
                openDialog = false
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateOrder(
                    orderId = orderId,
                    objectId = objectId,
                    updateObject = UpdateObjectRequestDTO(action = Action.REMOVE_OBJECT)
                )
            }
        )
    }
    ObserveNetworkStateHandlerRemoveObject(
        viewModel = viewModel,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onError = {
            observer = it
        },
        onSuccessful = {
            onSuccess()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerRemoveObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeObject }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.REMOVE_OBJECT)
            onSuccessful()
        }
    )
}

@Composable
private fun RemoveObject(
    onDismiss: () -> Unit = {},
    onItemSelected: () -> Unit = {}
) {
    SelectObject(
        onDismiss = onDismiss,
        body = {
            Title(
                title = DELETE_OBJECT,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = onItemSelected
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = onDismiss
            )
        }
    )
}
