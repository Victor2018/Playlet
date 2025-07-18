package com.victor.lib.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetworkUtils
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: Network Utility to detect availability or unavailability of Internet connection
 * -----------------------------------------------------------------
 */

object NetworkUtils : ConnectivityManager.NetworkCallback() {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Returns instance of [LiveData] which can be observed for network changes.
     */
    @SuppressLint("MissingPermission")
    fun getNetworkLiveData(context: Context): LiveData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        var isConnected = false

        // Retrieve current status of connectivity
        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)

            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                    return@forEach
                }
            }
        }

        networkLiveData.postValue(isConnected)

        return networkLiveData
    }

    override fun onAvailable(network: Network) {
        networkLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        networkLiveData.postValue(false)
    }

    /**
     * check NetworkAvailable
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val manager: ConnectivityManager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info = manager?.getActiveNetworkInfo()
            return null != info && info.isAvailable()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @SuppressLint("MissingPermission")
    fun isWifiConnected(context: Context): Boolean {
        var isWifi = true
        try {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager?.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                isWifi = capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } else {
                val wifiNetworkInfo = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                isWifi = wifiNetworkInfo?.isConnected ?: false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isWifi
    }
}
