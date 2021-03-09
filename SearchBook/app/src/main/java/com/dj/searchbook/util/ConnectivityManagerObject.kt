package com.dj.searchbook.util

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManagerObject
@Inject
constructor(
    application: Application
) {
    private val connectionLiveData = ConnectionLiveData(application)
    val isNetworkAvailable = MutableStateFlow(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let {
                isNetworkAvailable.value = it
            }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}