package com.android.architecture.helper

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {

    private val singleThread: Executor
    private val ioThread: Executor
    private val mainThread: Executor

    private constructor(singleThread: Executor, ioThread: Executor, mainThread: Executor) {
        this.singleThread = singleThread
        this.ioThread = ioThread
        this.mainThread = mainThread
    }

    private constructor() {
        singleThread = Executors.newSingleThreadExecutor()
        ioThread = Executors.newFixedThreadPool(THREAD_COUNT)
        mainThread = MainThreadExecutor()
    }

    fun single(): Executor {
        return singleThread
    }

    fun io(): Executor {
        return ioThread
    }

    fun main(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {

        private val looper = Looper.getMainLooper()
        private val mainThreadHandler = Handler(looper)

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }

    private object SingletonHolder {
        val holder = AppExecutors()
    }

    companion object {
        private const val THREAD_COUNT = 3

        @JvmStatic
        fun getInstance() = SingletonHolder.holder
    }

}