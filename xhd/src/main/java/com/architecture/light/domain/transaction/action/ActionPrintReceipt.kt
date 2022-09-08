package com.architecture.light.domain.transaction.action

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.ITask
import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.helper.TaskTimer
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import kotlinx.coroutines.*

class ActionPrintReceipt(listener: ActionStartListener) : AAction(listener) {

    private lateinit var job: Job
    private lateinit var task: ITask<TransData, TransData>
    private lateinit var transData: TransData
    private var activity: BaseActivity? = null
    private var delayRequestTime: Long = 0
    private val timer = TaskTimer {
        job.cancel()
        setResult(ActionResult(AppErrorCode.TASK_TIMEOUT, transData))
    }

    fun setParam(
        task: ITask<TransData, TransData>,
        transData: TransData,
        activity: BaseActivity?,
        delayRequestTime: Long = 0
    ) {
        this.task = task
        this.transData = transData
        this.activity = activity
        this.delayRequestTime = delayRequestTime
    }

    override fun isSingleAction(): Boolean {
        return true
    }

    override fun onExecute() {
        val connected = NetworkUtils.isConnected()
        if (!connected) {
            setResult(ActionResult(ErrorCode.NETWORK_NO_CONNECTION))
            return
        }
        job = MainScope().launch {
            timer.start()
            showLoading()
            delay(delayRequestTime)
            withContext(Dispatchers.IO) {
                task.execute(transData)
            }
            hideLoading()
            timer.stop()
            setResult(ActionResult(ErrorCode.SUCCESS))
        }
    }

    private fun showLoading() {
        activity?.let {
            if (it is AppActivityForAction) it.showLoading()
        }
    }

    private fun hideLoading() {
        activity?.let {
            if (it is AppActivityForAction) it.hideLoading()
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

}