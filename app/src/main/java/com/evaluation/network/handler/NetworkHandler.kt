package com.evaluation.network.handler

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class NetworkHandler @Inject constructor() {

    val isConnected: Boolean
        get() = try {
            val timeoutMs = 5000
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
}