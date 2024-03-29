package com.takehomechallenge.mohamadajihermansya.data.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.ApiClient
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.ApiService

class Apps : Application() {

    companion object {

        lateinit var instance: Apps
            private set

        fun hasConnection() = instance.checkNetworkStatus()

        private var apiInstance: ApiService? = null
        val api: ApiService
            get() {
                if (apiInstance == null) {
                    apiInstance = ApiClient.apiservice
                }
                return apiInstance!!
            }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun checkNetworkStatus(): Boolean {
        var isConnected = false
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isConnected = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    isConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return isConnected
    }

    fun getApiService() = api

}
