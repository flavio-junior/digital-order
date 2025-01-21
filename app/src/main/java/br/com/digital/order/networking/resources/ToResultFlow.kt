package br.com.digital.order.networking.resources

import android.content.Context
import br.com.digital.order.networking.resources.StatusCode.NUMBER_200
import br.com.digital.order.networking.resources.StatusCode.NUMBER_299
import br.com.digital.order.networking.resources.StatusCode.NUMBER_400
import br.com.digital.order.networking.resources.StatusCode.NUMBER_499
import br.com.digital.order.networking.resources.StatusCode.NUMBER_500
import br.com.digital.order.networking.resources.StatusCode.NUMBER_599
import br.com.digital.order.networking.resources.VerifyConnection.hasInternetConnection
import br.com.digital.order.utils.OrdersUtils.ERROR_CONNECTION_WITH_NETWORKING
import br.com.digital.order.utils.OrdersUtils.ERROR_INTERNAL_SERVER
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.EOFException
import java.net.SocketTimeoutException

inline fun <reified T> toResultFlow(context: Context, crossinline call: suspend () -> Response<T>):
        Flow<ObserveNetworkStateHandler<T>> {
    return flow {
        if (hasInternetConnection(context)) {
            emit(ObserveNetworkStateHandler.Loading(l = true))
            try {
                val c: Response<T> = call()
                if (c.isSuccessful && c.code() in NUMBER_200..NUMBER_299) {
                    emit(ObserveNetworkStateHandler.Success(s = c.body()))
                } else if (c.code() in NUMBER_400..NUMBER_499) {
                    val error: ResponseError = Gson().fromJson(
                        c.errorBody()?.string(),
                        ResponseError::class.java
                    )
                    emit(ObserveNetworkStateHandler.Error(e = translateResponseError(message = error.message)))
                } else if (c.code() in NUMBER_500..NUMBER_599) {
                    emit(ObserveNetworkStateHandler.Error(type = ErrorType.SERVER, e = ERROR_INTERNAL_SERVER))
                }
            } catch (e: EOFException) {
                emit(ObserveNetworkStateHandler.Error(type = ErrorType.INTERNAL, e = e.message.orEmpty()))
            } catch (h: HttpException) {
                emit(ObserveNetworkStateHandler.Error(type = ErrorType.INTERNAL, e = h.message.orEmpty()))
            } catch (s: SocketTimeoutException) {
                emit(ObserveNetworkStateHandler.Error(type = ErrorType.INTERNAL, e = s.message.orEmpty()))
            }
        } else {
            emit(ObserveNetworkStateHandler.Error(type = ErrorType.EXTERNAL, e = ERROR_CONNECTION_WITH_NETWORKING))
        }
    }.flowOn(Dispatchers.IO)
}
