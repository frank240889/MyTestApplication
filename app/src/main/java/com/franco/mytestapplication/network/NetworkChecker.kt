package com.franco.mytestapplication.network

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Verifies internet connectivity.
 *
 * @author Franco Omar Castillo Bello
 * Created 27/09/2021 at 08:00 a. m.
 */
@Singleton
class NetworkChecker @Inject constructor(
    private val application: Application
): MutableLiveData<Boolean>() {

    /**
     * Depending on what version of OS is running, one or another method will be initialized to
     * detect network changes.
     */
    private val connectivityManager: ConnectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val connectivityCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) = postValue(true)

            override fun onLost(network: Network) = postValue(false)

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Timber.e("The default network changed capabilities: $networkCapabilities")
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                Timber.e("The default network changed link properties: $linkProperties")
            }
        }
    }

    private val connectivityReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver(){
            @Suppress("DEPRECATION")
            override fun onReceive(context: Context?, intent: Intent?) {
                val conn = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo: NetworkInfo? = conn.activeNetworkInfo
                postValue(networkInfo?.isConnected ?: false)
            }
        }
    }


    init {
        // Initially assume there are not connection.
        postValue(false)
    }

    override fun onActive() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            @Suppress("DEPRECATION")
            application.registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
        else {
            connectivityManager.registerDefaultNetworkCallback(connectivityCallback)
        }
    }

    override fun onInactive() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            application.unregisterReceiver(connectivityReceiver)
        }
        else {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }
    }
}