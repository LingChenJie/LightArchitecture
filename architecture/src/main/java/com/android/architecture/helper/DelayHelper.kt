package com.android.architecture.helper

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.SparseArray

object DelayHelper {
    private const val TAG = "DelayHelper"
    private val delayTaskList = SparseArray<Task>()

    private val HANDLER = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val what = msg.what
            Logger.d(TAG, "handleMessage what:$what")
            val task = delayTaskList[what]
            task?.execute()
        }
    }

    /**
     * 发送一个延时任务
     */
    fun sendDelayTask(what: Int, delayMillis: Long, task: Task) {
        // 移除之前已存在相同的任务
        HANDLER.removeMessages(what)
        delayTaskList.remove(what)
        // 添加任务
        delayTaskList.put(what, task)
        HANDLER.sendEmptyMessageDelayed(what, delayMillis)
    }

    fun sendDelayTask(what: Int, delayMillis: Long) {
        HANDLER.sendEmptyMessageDelayed(what, delayMillis)
    }

    /**
     * 发送一个延时任务
     */
    fun sendDelayTask(delayMillis: Long, task: Task) {
        HANDLER.postDelayed({
            task.execute()
        }, delayMillis)
    }

    fun sendDelayTask(delayMillis: Long, task: () -> Unit) {
        HANDLER.postDelayed(task, delayMillis)
    }

    /**
     * 移除任务
     */
    fun removeTask(what: Int) {
        HANDLER.removeMessages(what)
        delayTaskList.remove(what)
    }

    interface Task {
        fun execute()
    }

}