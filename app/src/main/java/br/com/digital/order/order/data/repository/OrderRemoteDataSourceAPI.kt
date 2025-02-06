package br.com.digital.order.order.data.repository

import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.order.data.dto.OrdersResponseDTO
import br.com.digital.order.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.order.reservation.data.dto.ReservationResponseDTO
import br.com.digital.order.utils.NumbersUtils
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderRemoteDataSourceAPI {

    @GET("/api/dashboard/company/orders/v1/open")
    suspend fun findAllOpenOrders(
        @Query("page") page: Int,
        @Query("size") size: Int = NumbersUtils.NUMBER_SIXTY,
        @Query("sort") sort: String
    ): Response<OrdersResponseDTO>

    @PUT("/api/dashboard/company/orders/v1/{orderId}/update/object/{objectId}")
    suspend fun updateOrder(
        @Path("orderId") orderId: Long,
        @Path("objectId") objectId: Long,
        @Body updateObject: UpdateObjectRequestDTO
    ): Response<Unit>

    @POST("/api/dashboard/company/orders/v1/{orderId}/increment/more/objects/order")
    suspend fun incrementMoreObjectsOrder(
        @Path("orderId") orderId: Long,
        @Body incrementObjects: List<ObjectRequestDTO>
    ): Response<Unit>

    @POST("/api/dashboard/company/orders/v1/{orderId}/increment/more/reservations/order")
    suspend fun incrementMoreReservationsOrder(
        @Path("orderId") orderId: Long,
        @Body reservationsToSava: List<ReservationResponseDTO>
    ): Response<Unit>

    @DELETE("/api/dashboard/company/orders/v1/{orderId}/remove/reservation/{reservationId}")
    suspend fun removeReservationOrder(
        @Path("orderId") orderId: Long,
        @Path("reservationId") reservationId: Long
    ): Response<Unit>

    @DELETE("/api/dashboard/company/orders/v1/{orderId}")
    suspend fun deleteOrder(@Path("orderId") orderId: Long): Response<Unit>
}
