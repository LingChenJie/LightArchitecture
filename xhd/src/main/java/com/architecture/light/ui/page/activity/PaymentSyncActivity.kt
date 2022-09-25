package com.architecture.light.ui.page.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityPaymentSyncBinding
import com.architecture.light.domain.task.NotifyCollectionTask
import com.architecture.light.domain.task.PayQueryTask
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.domain.transaction.action.ActionHttpTask
import com.architecture.light.domain.transaction.action.ActionPayTask
import com.architecture.light.ext.toastSucc
import com.architecture.light.ext.toastWarn
import com.architecture.light.ui.adapter.PaymentSyncAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class PaymentSyncActivity : AppActivity() {

    private val binding: ActivityPaymentSyncBinding by lazy {
        ActivityPaymentSyncBinding.inflate(
            layoutInflater
        )
    }

    private val adapter by lazy { PaymentSyncAdapter() }

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
            val transList = TransDataModel.queryPaymentTimeout2SyncFailedTrans()
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
            (it as ActionPayTask).setParam(PayQueryTask(), transData, this)
        }
        actionPayQueryTask.setEndListener { _, result ->
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
            (it as ActionHttpTask).setParam(NotifyCollectionTask(), transData, this)
        }
        actionNotifyCollectionTask.setEndListener { _, result ->
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

    private fun updateTransData(transData: TransData) {
        AppExecutors.getInstance().single().execute {
            val result = TransDataModel.update(transData)
            Logger.e(TAG, "updateTransData result:$result")
        }
    }

}