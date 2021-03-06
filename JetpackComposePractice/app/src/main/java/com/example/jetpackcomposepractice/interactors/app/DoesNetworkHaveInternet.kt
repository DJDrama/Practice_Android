package com.example.jetpackcomposepractice.interactors.app

import android.util.Log
import com.example.jetpackcomposepractice.util.TAG
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.net.SocketFactory

object DoesNetworkHaveInternet {
    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            Log.d(TAG, "execute: Pining Google")
            val socket = socketFactory.createSocket()
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "execute: Ping Success")
            true
        } catch (e: IOException) {
            Log.e(TAG, "execute: No Internet Connection $e")
            false
        }
    }
}