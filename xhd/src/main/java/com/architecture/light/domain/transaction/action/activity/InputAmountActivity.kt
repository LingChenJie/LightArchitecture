package com.architecture.light.domain.transaction.action.activity

import android.text.InputFilter
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.valid
import com.android.architecture.helper.AmountInputFilter
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputAmountBinding
import com.architecture.light.domain.transaction.action.ActionInputAmount
import com.architecture.light.domain.transaction.action.ActionInputTel
import com.architecture.light.ui.view.NumberKeyboardView

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputAmountActivity : AppActivityForAction() {

    private val binding: ActivityInputAmountBinding by lazy {
        ActivityInputAmountBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        val filters = arrayOf<InputFilter>(AmountInputFilter())
        binding.etAmount.filters = filters
        binding.keyboard.bindEditText(binding.etAmount)
        binding.keyboard.setOnInputListener(object : NumberKeyboardView.OnInputListener {
            override fun textChanged() {
                notifyConfirmEnabled()
            }

            override fun confirm() {
                clickConfirm()
            }
        })
        notifyConfirmEnabled()
    }

    private fun clickConfirm() {
        val amount = binding.etAmount.text.toString()
        if (amount.valid) {
            val info = ActionInputAmount.Info(amount.toDouble())
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
    }

    private fun notifyConfirmEnabled() {
        val amount = binding.etAmount.text.toString()
        if (amount.valid && amount.toDouble() > 0) {
            binding.keyboard.setConfirmEnabled(true)
        } else {
            binding.keyboard.setConfirmEnabled(false)
        }
    }

}