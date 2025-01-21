package br.com.digital.order.networking.resources

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.io.EOFException

object VerifyConnection {
    fun hasInternetConnection(context: Context?): Boolean {
        return try {
            val connectivityManager: ConnectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            networkCapabilities?.let { capabilities ->
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) &&
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) &&
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                true
            } ?: false
        } catch (e: EOFException) {
            e.message?.let { Log.e("VERIFY_CONNECTION", it) }
            false
        }
    }
}
