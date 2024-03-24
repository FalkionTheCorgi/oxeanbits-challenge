package com.example.oxeanbits_challenge.network_monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkMonitor(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkInfo != null && networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}