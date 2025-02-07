package br.com.digital.order.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.R
import br.com.digital.order.dashboard.domain.others.Action
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.order.domain.factory.statusObject
import br.com.digital.order.order.ui.viewmodel.OrderViewModel
import br.com.digital.order.order.ui.viewmodel.ResetOrder
import br.com.digital.order.reservation.data.vo.OverviewResponseVO
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.SimpleText
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.OrdersUtils.WEIGHT_SIZE
import br.com.digital.order.utils.OrdersUtils.WEIGHT_SIZE_2
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import br.com.digital.order.utils.StringsUtils.DELETE_RESUME
import br.com.digital.order.utils.onBorder
import br.com.digital.order.utils.onClickable
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardOverview(
    orderId: Long? = null,
    objectId: Long? = null,
    overviews: List<OverviewResponseVO>? = null,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    var deleteOverview by remember { mutableStateOf(value = false) }
    val viewModel: OrderViewModel = koinViewModel()
    var overviewId: Long by remember { mutableLongStateOf(value = 1) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.onBorder(
            color = Themes.colors.primary,
            spaceSize = Themes.size.spaceSize16,
            width = Themes.size.spaceSize2
        ),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        overviews?.forEach { overview ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Themes.size.spaceSize16,
                        start = Themes.size.spaceSize16,
                        end = Themes.size.spaceSize16
                    )
            ) {
                Description(
                    description = overview.quantity.toString(),
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Center
                )
                SimpleText(
                    text = statusObject(status = overview.status),
                    modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
                    textAlign = TextAlign.Center
                )
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = Themes.colors.primary,
                    modifier = Modifier.onClickable {
                        deleteOverview = true
                        overviewId = overview.id
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
    }
    if (deleteOverview) {
        ActionsToOverview(
            overviewId = overviewId,
            onDismiss = {
                deleteOverview = false
            },
            onItemSelected = { id ->
                deleteOverview = false
                viewModel.updateOrder(
                    orderId = orderId ?: 0,
                    objectId = objectId ?: 0,
                    updateObject = UpdateObjectRequestDTO(
                        action = Action.REMOVE_OVERVIEW,
                        overview = id
                    )
                )
            }
        )
    }
    ObserveNetworkStateHandlerRemoveOverview(
        viewModel = viewModel,
        onError = {},
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = onSuccess
    )
}

@Composable
private fun ObserveNetworkStateHandlerRemoveOverview(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeOverview }
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
            viewModel.resetOrder(reset = ResetOrder.REMOVE_OVERVIEW)
            onSuccessful()
        }
    )
}

@Composable
private fun ActionsToOverview(
    overviewId: Long,
    onDismiss: () -> Unit = {},
    onItemSelected: (Long) -> Unit = {}
) {
    SelectObject(
        onDismiss = onDismiss,
        body = {
            Title(
                title = DELETE_RESUME,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = {
                    onItemSelected(overviewId)
                }
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = onDismiss
            )
        }
    )
}