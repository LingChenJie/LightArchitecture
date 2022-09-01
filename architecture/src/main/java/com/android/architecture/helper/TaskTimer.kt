package com.android.architecture.helper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message

@SuppressLint("HandlerLeak")
class TaskTimer(
    timeout: Int = 60,
    block: () -> Unit
) {

    companion object {
        const val TAG = "TaskTimer"
    }

    private var count: Int = 0
    private var timeout: Int = 0
    private val handler: Handler

    init {
        this.timeout = timeout
        val mainLooper = Looper.getMainLooper()
        handler = object : Handler(mainLooper) {

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                count++
                removeCallbacksAndMessages(null)
                if (count > timeout) {
                    Logger.e(TAG, "Timer Finish and Request Timeout")
                    block.invoke()
                } else {
                    sendEmptyMessageDelayed(0, 1000)
                }
            }

        }
    }

    fun setTimeout(timeout: Int) {
        this.timeout = timeout
    }

    fun start() {
        count = 0
        handler.removeCallbacksAndMessages(null)
        handler.sendEmptyMessageDelayed(0, 1000)
    }

    fun resume() {
        handler.removeCallbacksAndMessages(null)
        handler.sendEmptyMessageDelayed(0, 1000)
    }

    fun stop() {
        Logger.e(TAG, "Timer Cancel")
        handler.removeCallbacksAndMessages(null)
    }

}