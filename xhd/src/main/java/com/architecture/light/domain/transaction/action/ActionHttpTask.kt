package com.architecture.light.domain.transaction.action

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.ITask
import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.getString
import com.android.architecture.helper.TaskTimer
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.ui.page.BaseDialog
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.R
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.task.*
import com.architecture.light.ui.dialog.LoadingDialog
import kotlinx.coroutines.*

class ActionHttpTask(listener: ActionStartListener) : AAction(listener) {

    private lateinit var job: Job
    private lateinit var task: ITask<TransData, TransData>
    private lateinit var transData: TransData
    private var activity: BaseActivity? = null
    private var delayRequestTime: Long = 0
    private var loadingDialog: BaseDialog? = null
    private val timer = TaskTimer {
        job.cancel()
        setResult(ActionResult(AppErrorCode.TASK_TIMEOUT))
    }

    fun setParam(
        task: ITask<TransData, TransData>,
        transData: TransData,
        activity: BaseActivity?,
        delayRequestTime: Long = 500
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
        val message = when (task) {
            is LogonTask -> getString(R.string.loading_login)
            is SearchRoomTask -> getString(R.string.loading_room_query)
            is SearchReserveTask -> getString(R.string.loading_reserve_query)
            is SearchPaymentTask -> getString(R.string.loading_payment_query)
            is NotifyCollectionTask -> getString(R.string.loading_payment_notify)
            is NotifyPrepaidTask -> getString(R.string.loading_payment_notify)
            is NotifyVoidTask -> getString(R.string.loading_void_notify)
            is SearchBillTask -> getString(R.string.loading_bill_query)
            else -> getString(R.string.common_loading)
        }
        loadingDialog = LoadingDialog.Builder(activity)
            .setMessage(message)
            .create()
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

}