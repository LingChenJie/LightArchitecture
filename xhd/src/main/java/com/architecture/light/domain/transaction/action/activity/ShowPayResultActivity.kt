package com.architecture.light.domain.transaction.action.activity

import android.view.View
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityShowPayResultBinding
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.PayHelper

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
        binding.btConfirm.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btExit.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btRepay.click {
            finish(ActionResult(AppErrorCode.BACK_TO_CHOOSE_ROOM_PAGE))
        }
        binding.btRequery.click {
            finish(ActionResult(AppErrorCode.PAY_RESULT_QUERY))
        }
        binding.btResynch.click {
            finish(ActionResult(AppErrorCode.PAY_RESULT_NOTIFY))
        }
        val result = intent.getSerializableExtra(UIParams.ACTION_RESULT) as ActionResult
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        if (result.code == ErrorCode.SUCCESS) {
            val code = transData.responseCode
            val message = transData.responseMessage
            if (code == ErrorCode.SUCCESS) {
                val payData = transData.payData!!
                binding.ivResult.setImageResource(R.drawable.icon_result_success)
                binding.tvResultTip.isSelected = false
                binding.tvFailMessage.visibility = View.GONE
                binding.tvFailMessage.text = ""
                binding.tvThisPaymentAmount.text = AmountHelper.formatAmount(payData.amt.toLong())
                binding.tvPaymentMethod.text =
                    PayHelper.getTransactionPlatform(transData.transactionPlatform)
                binding.tvPaymentBankName.text = transData.bankName
                binding.tvPaymentBankAccount.text = transData.bankAccount
                binding.layoutBottomSuccess.visibility = View.VISIBLE
                binding.layoutBottomFail.visibility = View.GONE
                finish(ActionResult(AppErrorCode.PAY_RESULT_NOTIFY))
            } else {
                binding.ivResult.setImageResource(R.drawable.icon_result_fail)
                binding.tvResultTip.isSelected = true
                binding.tvFailMessage.visibility = View.VISIBLE
                binding.tvFailMessage.text = "$message[$code]"
                binding.layoutBottomSuccess.visibility = View.GONE
                binding.layoutBottomFail.visibility = View.VISIBLE
            }
        } else {
            val code = result.code
            val message = result.message ?: ErrorCode.getMessage(code)
            binding.ivResult.setImageResource(R.drawable.icon_result_fail)
            binding.tvResultTip.isSelected = true
            binding.tvFailMessage.visibility = View.VISIBLE
            binding.tvFailMessage.text = "$message[$code]"
            binding.layoutBottomSuccess.visibility = View.GONE
            binding.layoutBottomFail.visibility = View.VISIBLE
        }

    }

}