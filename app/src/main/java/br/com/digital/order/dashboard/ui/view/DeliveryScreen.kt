package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.order.R
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.dashboard.data.dto.AddressRequestDTO
import br.com.digital.order.dashboard.data.dto.OrderRequestDTO
import br.com.digital.order.dashboard.domain.type.TypeOrder
import br.com.digital.order.dashboard.ui.viewmodel.DashboardViewModel
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.IsErrorMessage
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.TextField
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.OrdersUtils.NUMBER_EQUALS_ZERO
import br.com.digital.order.utils.StringsUtils.CREATE_NEW_ORDER
import br.com.digital.order.utils.StringsUtils.NOT_BLANK_OR_EMPTY
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryScreen(
    goToBack: () -> Unit = { },
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: DashboardViewModel = koinViewModel()
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var address: AddressRequestDTO by remember { mutableStateOf(value = AddressRequestDTO()) }
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
            title = R.string.delivery,
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
            BodyAddress(
                isError = observer.second,
                onResult = {
                    address = it
                }
            )
            BodyRequestsOrder(
                objectsToSave = objectsToSave,
                contentObjects = contentObjects,
                verifyObjects = verifyObjects,
                goToAlternativeRoutes = goToAlternativeRoutes,
            )
            LoadingButton(
                label = CREATE_NEW_ORDER,
                onClick = {
                    if (objectsToSave.isEmpty()) {
                        observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                    } else if (checkAddressIsNull(address)) {
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
                                    type = TypeOrder.DELIVERY,
                                    address = address,
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
    ObserveNetworkStateHandlerCreateNewOrderDelivery(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = goToBack
    )
}

@Composable
private fun BodyAddress(
    isError: Boolean,
    onResult: (AddressRequestDTO) -> Unit = {}
) {
    var street: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var number: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    var district: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var complement: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    TextField(
        label = R.string.street,
        value = street,
        isError = isError,
        icon = R.drawable.home_pin,
        onValueChange = {
            if (it.length <= 30) {
                street = it
            }
        },
    )
    TextField(
        label = R.string.number,
        value = number.toString(),
        isError = isError,
        icon = R.drawable.dialpad,
        keyboardType = KeyboardType.Number,
        onValueChange = {
            if (it.length <= 6) {
                number = it.toIntOrNull() ?: NUMBER_ZERO
            }
        },
    )
    TextField(
        label = R.string.district,
        value = district,
        isError = isError,
        icon = R.drawable.personal_places,
        onValueChange = {
            if (it.length <= 20) {
                district = it
            }
        },
    )
    TextField(
        label = R.string.complement,
        value = complement,
        isError = isError,
        icon = R.drawable.description,
        onValueChange = {
            if (it.length <= 50) {
                complement = it
            }
        }
    )
    onResult(
        AddressRequestDTO(
            street = street,
            number = number,
            district = district,
            complement = complement
        )
    )
}

private fun checkAddressIsNull(
    address: AddressRequestDTO,
): Boolean {
    return address.street.isEmpty() || address.number == NUMBER_ZERO || address.district.isEmpty() ||
            address.complement.isEmpty()
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewOrderDelivery(
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
