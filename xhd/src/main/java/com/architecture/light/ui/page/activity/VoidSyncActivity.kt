package com.architecture.light.ui.page.activity

import android.view.View
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityVoidSyncBinding
import com.architecture.light.domain.task.NotifyVoidTask
import com.architecture.light.domain.task.VoidQueryTask
import com.architecture.light.domain.transaction.action.ActionHttpTask
import com.architecture.light.domain.transaction.action.ActionPayTask
import com.architecture.light.ext.toastSucc
import com.architecture.light.ext.toastWarn
import com.architecture.light.ui.adapter.VoidSyncAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class VoidSyncActivity : AppActivity() {

    private val binding: ActivityVoidSyncBinding by lazy {
        ActivityVoidSyncBinding.inflate(
            layoutInflater
        )
    }

    private val adapter by lazy { VoidSyncAdapter() }

    override fun onResume() {
        super.onResume()
        TransactionConstant.getInstance().currentActivity = this
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
        binding.btConfirm.click { finish() }
        adapter.setItemClickListener { _, _, item ->
            syncTransData(item)
        }
        binding.recyclerView.adapter = adapter
        refreshUI()
    }

    private fun refreshUI() {
        showLoading()
        AppExecutors.getInstance().single().execute {
            val transList = TransDataModel.queryVoidTimeout2SyncFailedTrans()
            AppExecutors.getInstance().main().execute {
                hideLoading()
                if (transList.isNotEmpty()) {
                    binding.layoutData.visibility = View.VISIBLE
                    binding.layoutNoData.visibility = View.GONE
                    adapter.data = transList
                    adapter.notifyDataSetChanged()
                } else {
                    binding.layoutData.visibility = View.GONE
                    binding.layoutNoData.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun syncTransData(trans: TransData) {
        when (trans.transactionStatus) {
            TransactionStatus.TransTimeout.name -> {
                transQuery(trans)
            }
            TransactionStatus.TransSucceed.name,
            TransactionStatus.ResultNotifyFailed.name
            -> {
                notifyResult(trans)
            }
        }
    }

    private fun transQuery(transData: TransData) {
        val actionPayQueryTask = ActionPayTask {
            (it as ActionPayTask).setParam(VoidQueryTask(), transData)
        }
        actionPayQueryTask.setEndListener { _, result ->
            setTransactionStatusMessage(result, transData)
            if (result.code == ErrorCode.SUCCESS) {
                when (transData.responseCode) {
                    ErrorCode.SUCCESS -> {
                        transData.transactionStatus = TransactionStatus.TransSucceed.name
                        updateTransData(transData)
                        syncTransData(transData)
                    }
                    AppErrorCode.PAY_TIMEOUT -> {
                        transData.transactionStatus = TransactionStatus.TransTimeout.name
                        updateTransData(transData)
                        toastTransResult(transData)
                    }
                    else -> {
                        transData.transactionStatus = TransactionStatus.TransFailed.name
                        updateTransData(transData)
                        toastTransResult(transData)
                        refreshUI()
                    }
                }
            } else {
                toastActionResult(result)
            }
        }.execute()
    }

    private fun notifyResult(transData: TransData) {
        val actionNotifyCollectionTask = ActionHttpTask {
            (it as ActionHttpTask).setParam(NotifyVoidTask(), transData)
        }
        actionNotifyCollectionTask.setEndListener { _, result ->
            setTransactionStatusMessage(result, transData)
            if (result.code == ErrorCode.SUCCESS) {
                when (transData.responseCode) {
                    ErrorCode.SUCCESS -> {
                        transData.transactionStatus = TransactionStatus.ResultNotifySucceed.name
                        updateTransData(transData)
                        toastTransResult(transData)
                        refreshUI()
                    }
                    else -> {
                        transData.transactionStatus = TransactionStatus.ResultNotifyFailed.name
                        updateTransData(transData)
                        toastTransResult(transData)
                        refreshUI()
                    }
                }
            } else {
                toastActionResult(result)
            }
        }.execute()
    }

    private fun toastActionResult(result: ActionResult) {
        val code = result.code
        val message = result.message ?: ErrorCode.getMessage(code)
        toastWarn("$message[$code]")
    }

    private fun toastTransResult(transData: TransData) {
        val code = transData.responseCode
        val message = transData.responseMessage
        if (code == ErrorCode.SUCCESS) {
            toastSucc("$message[$code]")
        } else {
            toastWarn("$message[$code]")
        }
    }

    private fun setTransactionStatusMessage(actionResult: ActionResult, transData: TransData) {
        val code = actionResult.code
        if (code == ErrorCode.SUCCESS) {
            val responseCode = transData.responseCode
            val responseMessage = transData.responseMessage
            transData.transactionStatusMessage = "$responseMessage[$responseCode]"
        } else {
            val message = actionResult.message ?: ErrorCode.getMessage(code)
            transData.transactionStatusMessage = "$message[$code]"
        }
    }

    private fun updateTransData(transData: TransData) {
        AppExecutors.getInstance().single().execute {
            val result = TransDataModel.update(transData)
            Logger.e(TAG, "updateTransData result:$result")
        }
    }

}