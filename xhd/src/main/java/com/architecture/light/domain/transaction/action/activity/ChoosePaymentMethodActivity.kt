package com.architecture.light.domain.transaction.action.activity

import android.view.View
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.TransactionPlatform
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.databinding.ActivityChoosePaymentMethodBinding
import com.architecture.light.domain.transaction.action.ActionChoosePaymentMethod
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.helper.AmountHelper
import com.architecture.light.settings.ProjectCache
import com.architecture.light.ui.popup.BankAccountListPopup

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChoosePaymentMethodActivity : AppActivityForAction() {

    private val binding: ActivityChoosePaymentMethodBinding by lazy {
        ActivityChoosePaymentMethodBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        val titleName = intent.getStringExtra(UIParams.TITLE_NAME)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        val showUnpaidAmount = intent.getBooleanExtra(UIParams.SHOW_UNPAID_AMOUNT, true)
        binding.titleView.titleText.text = titleName
        if (showUnpaidAmount) {
            binding.layoutUnpaidAmount.visibility = View.VISIBLE
        } else {
            binding.layoutUnpaidAmount.visibility = View.GONE
        }
        binding.tvAmountUnpaid.text = AmountHelper.formatAmount(transData.unpaidAmount)
        binding.tvAmountThisTime.text = AmountHelper.formatAmount(transData.amount)
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
        binding.layoutChooseBankAccount.click {
            showBankAccountList()
        }

        binding.btConfirm.click {
            val info = ActionChoosePaymentMethod.PaymentMethodInfo(
                transactionPlatform,
                bankAccount,
                bankName
            )
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
    }

    private var transactionPlatform = TransactionPlatform.Bank
    private var bankName = ""
    private var bankAccount = ""

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

    private fun showBankAccountList() {
        val project = ProjectCache.getProject()!!
        val bankList = mutableListOf<LoginResponse.Data.Project.Bank>()
        for (bank in project.bankList) {
            if (bank.bankAccount.valid && bank.bankName.valid) {
                bankList.add(bank)
            }
        }
        BankAccountListPopup.Builder(this)
            .setList(bankList)
            .setSelectListener { _, _, item ->
                binding.tvPleaseChooseAccountTip.visibility = View.GONE
                binding.layoutBankAccount.visibility = View.VISIBLE
                binding.tvBankName.text = item.bankName
                binding.tvBankAccount.text = item.bankAccount
                bankName = item.bankName
                bankAccount = item.bankAccount
                notifyUI()
            }
            .showAsDropDown(binding.layoutChooseBankAccount)
    }

    private fun notifyUI() {
        binding.btConfirm.isEnabled = !(bankName.empty || bankAccount.empty)
    }

}