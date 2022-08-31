package com.android.architecture.data.remote

import com.android.architecture.data.remote.ssl.SSLUtils
import com.android.architecture.helper.Logger
import com.android.architecture.utils.CalcUtils
import com.android.architecture.utils.CloseableUtils
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException

class SocketRequest {

    companion object {
        const val TAG = "SocketsRequest"
    }

    private var ip: String = ""
    private var port: Int = 0
    private var timeout: Int = 38 * 1000
    private var sslParams: SSLUtils.SSLParams? = null

    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    fun setTimeout(timeout: Int) {
        this.timeout = timeout * 1000
    }

    fun setIpAndPort(ip: String, port: Int) {
        this.ip = ip
        this.port = port
    }

    fun setSSLParams(sslParams: SSLUtils.SSLParams) {
        this.sslParams = sslParams
    }

    fun call(dataIn: ByteArray, dataOut: ByteArray, dataOutLen: IntArray) {
        if (ip == "") {
            throw RuntimeException("ip cannot be null")
        }
        try {
            Logger.d(TAG, "Connect to $ip:$port")
            val inetSocketAddress: InetSocketAddress
            try {
                var bool = false
                val charArray: CharArray = ip.toCharArray()
                for (c in charArray) {
                    bool = Character.isLowerCase(c) || Character.isUpperCase(c)
                    if (bool) break
                }
                if (bool) {
                    val inetAddress = InetAddress.getByName(ip)
                    inetSocketAddress = InetSocketAddress(inetAddress, port)
                } else {
                    inetSocketAddress = InetSocketAddress(ip, port)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(e.message)
            }

            if (sslParams != null) {
                socket = sslParams!!.sslSocketFactory.createSocket()
            } else {
                socket = Socket()
            }
            socket?.let { socket ->
                socket.tcpNoDelay = true
                socket.soTimeout = timeout  // 设置read阻塞的超时时间
                // socket.setSoLinger(true, 0)
                socket.connect(inetSocketAddress, timeout)
                Logger.e(TAG, "Connect success")
                inputStream = socket.getInputStream()
                outputStream = socket.getOutputStream()
            }

            outputStream?.let { outputStream ->
                outputStream.write(dataIn)
                outputStream.flush()
            }

            inputStream?.let { inputStream ->
                val currentTime = System.currentTimeMillis()
                var index = 0
                var len = 2// 前两个字节表示报文长度
                // 处理网络环境不好时，数据的组包、完整性。
                while (true) {
                    if (isTimeout(currentTime)) {
                        throw SocketTimeoutException("读取数据超时了.")
                    }
                    index += inputStream.read(dataOut, index, len)
                    if (index <= 0) {
                        index = 0
                        Thread.sleep(200)
                        continue
                    }
                    len = CalcUtils.getDataLen(dataOut)
                    if (isReceiveOver(dataOut, index)) {
                        break;
                    } else {
                        Thread.sleep(10)
                    }
                }

                // 接收的总字节数
                dataOutLen[0] = index
            }
        } finally {
            close()
        }
    }

    /**
     * 判断数据是否接收完毕
     */
    private fun isReceiveOver(data: ByteArray, length: Int): Boolean {
        if (length < 2) {
            return false
        }
        val calcLength = CalcUtils.getDataLen(data) + 2
        if (length < calcLength) {
            return false
        }
        return true
    }

    private fun close() {
        CloseableUtils.close(inputStream)
        CloseableUtils.close(outputStream)
        CloseableUtils.close(socket)
        inputStream = null
        outputStream = null
        socket = null
    }

    var timeInterval: Long = 0

    private fun isTimeout(timeMillis: Long): Boolean {
        timeInterval = System.currentTimeMillis() - timeMillis
        if (timeInterval > timeout) {
            return true;
        }
        return false
    }

}