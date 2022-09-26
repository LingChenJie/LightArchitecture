package com.architecture.light.domain.transaction.action

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.task.ITask
import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.getString
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.ui.page.BaseDialog
import com.architecture.light.R
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.task.*
import com.architecture.light.ui.dialog.LoadingDialog
import kotlinx.coroutines.*

class ActionPayTask(listener: ActionStartListener) : AAction(listener) {

    private lateinit var job: Job
    private lateinit var task: ITask<TransData, TransData>
    private lateinit var transData: TransData
    private var activity: BaseActivity? = null
    private var delayRequestTime: Long = 0
    private var loadingDialog: BaseDialog? = null

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
        job = MainScope().launch {
            showLoading()
            delay(delayRequestTime)
            withContext(Dispatchers.IO) {
                task.execute(transData)
            }
            hideLoading()
            setResult(ActionResult(ErrorCode.SUCCESS))
        }
    }

    private fun showLoading() {
        val message = when (task) {
            is BankPayTask -> getString(R.string.loading_pay_request)
            is CodePayTask -> getString(R.string.loading_pay_request)
            is BankVoidTask -> getString(R.string.loading_void_request)
            is CodeVoidTask -> getString(R.string.loading_void_request)
            is PayQueryTask -> getString(R.string.loading_pay_query)
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