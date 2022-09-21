package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.databinding.ActivityChooseOriginalPaymentMethodBinding
import com.architecture.light.domain.transaction.action.ActionChooseOriginalPaymentMethod

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChooseOriginalPaymentMethodActivity : AppActivityForAction() {

    private val binding: ActivityChooseOriginalPaymentMethodBinding by lazy {
        ActivityChooseOriginalPaymentMethodBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.layoutBankcard.isSelected = true
        binding.btConfirm.isEnabled = false
        binding.layoutBankcard.click {
            selectBankCard()
        }
        binding.layoutAlipay.click {
            selectAlipay()
        }
        binding.layoutWechat.click {
            selectWechat()
        }
        binding.btConfirm.click {
            val info = ActionChooseOriginalPaymentMethod.PaymentMethodInfo(transactionPlatform)
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
    }

    private var transactionPlatform = TransactionPlatform.Bank

    private fun selectBankCard() {
        transactionPlatform = TransactionPlatform.Bank
        binding.layoutBankcard.isSelected = true
        binding.layoutAlipay.isSelected = false
        binding.layoutWechat.isSelected = false
    }

    private fun selectAlipay() {
        transactionPlatform = TransactionPlatform.AliPay
        binding.layoutBankcard.isSelected = false
        binding.layoutAlipay.isSelected = true
        binding.layoutWechat.isSelected = false
    }

    private fun selectWechat() {
        transactionPlatform = TransactionPlatform.WechatPay
        binding.layoutBankcard.isSelected = false
        binding.layoutAlipay.isSelected = false
        binding.layoutWechat.isSelected = true
    }


}