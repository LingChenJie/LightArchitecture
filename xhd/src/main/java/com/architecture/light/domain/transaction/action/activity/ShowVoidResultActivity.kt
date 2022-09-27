package com.architecture.light.domain.transaction.action.activity

import android.content.Intent
import android.view.View
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityShowVoidResultBinding
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.TransHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ShowVoidResultActivity : AppActivityForAction() {

    private val binding: ActivityShowVoidResultBinding by lazy {
        ActivityShowVoidResultBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.visibility = View.GONE
        binding.btConfirm.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btExit.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btRevoid.click {
            finish(ActionResult(AppErrorCode.VOID_AGAIN))
        }
        binding.btRequery.click {
            finish(ActionResult(AppErrorCode.VOID_RESULT_QUERY))
        }
        binding.btResynch.click {
            finish(ActionResult(AppErrorCode.VOID_RESULT_NOTIFY))
        }
        refreshUI(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Logger.e(TAG, "--onNewIntent")
        resetFinishedFlag()
        refreshUI(intent)
    }

    private fun refreshUI(intent: Intent) {
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        binding.tvThisVoidAmount.text = AmountHelper.formatAmount(transData.amount)
        binding.tvPaymentMethod.text =
            TransHelper.getTransactionPlatform(transData.transactionPlatform)

        when (transData.transactionStatus) {
            TransactionStatus.TransSucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.void_result_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                finish(ActionResult(AppErrorCode.VOID_RESULT_NOTIFY))
            }
            TransactionStatus.TransFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.void_result_fail)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRevoid.visibility = View.VISIBLE
                binding.btRequery.visibility = View.GONE
                binding.btResynch.visibility = View.GONE
            }
            TransactionStatus.TransTimeout.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.void_result_timeout)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRevoid.visibility = View.GONE
                binding.btRequery.visibility = View.VISIBLE
                binding.btResynch.visibility = View.GONE
            }
            TransactionStatus.ResultNotifySucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.void_result_sync_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
            }
            TransactionStatus.ResultNotifyFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.void_result_sync_fail)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRevoid.visibility = View.GONE
                binding.btRequery.visibility = View.GONE
                binding.btResynch.visibility = View.VISIBLE
            }
        }
    }

}