package com.architecture.light.domain.transaction.action.activity

import android.content.Intent
import android.view.View
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger.e
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.TransactionStatus
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityShowPayResultBinding
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ext.toastSucc
import com.architecture.light.ext.toastWarn
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.TransHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ShowPayResultActivity : AppActivityForAction() {

    private val binding: ActivityShowPayResultBinding by lazy {
        ActivityShowPayResultBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        val titleName = intent.getStringExtra(UIParams.TITLE_NAME)
        binding.titleView.titleText.text = titleName
        binding.titleView.backView.visibility = View.GONE
        binding.btConfirm.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btExit.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btRepay.click {
            finish(ActionResult(AppErrorCode.PAY_AGAIN))
        }
        binding.btRequery.click {
            finish(ActionResult(AppErrorCode.PAY_RESULT_QUERY))
        }
        binding.btResynch.click {
            finish(ActionResult(AppErrorCode.PAY_RESULT_NOTIFY))
        }
        refreshUI(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        e(TAG, "--onNewIntent")
        refreshUI(intent)
    }

    private fun refreshUI(intent: Intent) {
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        binding.tvThisPaymentAmount.text = AmountHelper.formatAmount(transData.amount)
        binding.tvPaymentMethod.text =
            TransHelper.getTransactionPlatform(transData.transactionPlatform)
        binding.tvPaymentBankName.text = transData.bankName
        binding.tvPaymentBankAccount.text = transData.bankAccount

        when (transData.transactionStatus) {
            TransactionStatus.TransSucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_pay_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                finish(ActionResult(AppErrorCode.PAY_RESULT_NOTIFY))
            }
            TransactionStatus.TransFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.payment_result_pay_fail)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRepay.visibility = View.VISIBLE
                binding.btRequery.visibility = View.GONE
                binding.btResynch.visibility = View.GONE
            }
            TransactionStatus.TransTimeout.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.payment_result_pay_timeout)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRepay.visibility = View.GONE
                binding.btRequery.visibility = View.VISIBLE
                binding.btResynch.visibility = View.GONE
            }
            TransactionStatus.ResultNotifySucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_sync_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
//                binding.layoutBottomSuccess.visibility = View.VISIBLE
//                binding.layoutBottomFail.visibility = View.GONE
//                finish(ActionResult(AppErrorCode.BILL_GET))
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRepay.visibility = View.GONE
                binding.btRequery.visibility = View.GONE
                binding.btResynch.visibility = View.VISIBLE
            }
            TransactionStatus.ResultNotifyFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvResultTip.text = getString(R.string.payment_result_sync_fail)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
                binding.btRepay.visibility = View.GONE
                binding.btRequery.visibility = View.GONE
                binding.btResynch.visibility = View.VISIBLE
            }
            TransactionStatus.GetPrintDataSucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_sync_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                finish(ActionResult(AppErrorCode.BILL_PRINT))
            }
            TransactionStatus.GetPrintDataFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_sync_success)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
            }
            TransactionStatus.PrintSucceed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_sync_success)
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                toastSucc(transData.responseMessage)
            }
            TransactionStatus.PrintFailed.name -> {
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvResultTip.text = getString(R.string.payment_result_sync_success)
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = transData.transactionStatusMessage
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                toastWarn(transData.responseMessage)
            }
        }
    }

}