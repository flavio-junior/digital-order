package br.com.digital.order.ui.components

import androidx.compose.runtime.Composable
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ErrorType
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.networking.resources.StatusCode.NUMBER_403
import br.com.digital.order.networking.resources.selectAlternativeRoute
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.OrdersUtils.UNAUTHORIZED_MESSAGE

@Composable
fun <R : ObserveNetworkStateHandler<*>> ObserveNetworkStateHandler(
    state: R,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String?) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (R) -> Unit = {}
) {
    when (state) {
        is ObserveNetworkStateHandler.Loading<*> -> onLoading()

        is ObserveNetworkStateHandler.Error<*> -> {
            when (state.type) {
                ErrorType.CLIENT -> {
                    if (state.exception.code == NUMBER_403 && state.exception.message == UNAUTHORIZED_MESSAGE) {
                        goToAlternativeRoutes(selectAlternativeRoute(code = state.exception.code))
                    } else {
                        onError(state.e)
                    }
                }

                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    goToAlternativeRoutes(
                        selectAlternativeRoute(
                            code = state.exception.code ?: NUMBER_ZERO
                        )
                    )
                }
            }
        }

        is ObserveNetworkStateHandler.Success<*> -> onSuccess(state)
    }
}
