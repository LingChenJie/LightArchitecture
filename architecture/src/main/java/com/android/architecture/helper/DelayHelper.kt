package com.android.architecture.helper

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.SparseArray

object DelayHelper {
    const val TAG = "DelayTaskHelper"
    private val delayTaskList = SparseArray<Task>()

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            // 处理任务
            val what = msg.what
            Logger.d(TAG, "handleMessage what:$what")
            val task = delayTaskList[what]
            task?.execute()
        }
    }

    /**
     * 发送一个延时任务
     */
    fun sendDelayTask(what: Int, delay: Long, task: Task) {
        // 移除之前已存在相同的任务
        handler.removeMessages(what)
        delayTaskList.remove(what)
        // 添加任务
        delayTaskList.put(what, task)
        handler.sendEmptyMessageDelayed(what, delay)
    }

    fun sendDelayTask(what: Int, delay: Long) {
        handler.sendEmptyMessageDelayed(what, delay)
    }

    /**
     * 发送一个延时任务
     */
    fun sendDelayTask(delay: Long, task: Task) {
        handler.postDelayed({
            task.execute()
        }, delay)
    }

    /**
     * 移除任务
     */
    fun removeTask(what: Int) {
        handler.removeMessages(what)
        delayTaskList.remove(what)
    }

    interface Task {
        fun execute()
    }

}