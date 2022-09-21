package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputVoucherNumberBinding
import com.architecture.light.domain.transaction.action.ActionInputVoucherNumber
import com.architecture.light.ui.view.NumberKeyboardView

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputVoucherNumberActivity : AppActivityForAction() {

    private val binding: ActivityInputVoucherNumberBinding by lazy {
        ActivityInputVoucherNumberBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.keyboard.bindEditText(binding.etVoucherNumber)
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
        val voucherNumber = binding.etVoucherNumber.text.toString()
        if (voucherNumber.valid) {
            val telInfo = ActionInputVoucherNumber.Info(voucherNumber)
            finish(ActionResult(ErrorCode.SUCCESS, telInfo))
        }
    }

    private fun notifyConfirmEnabled() {
        val tel = binding.etVoucherNumber.text.toString()
        if (tel.valid && tel.length == 6) {
            binding.keyboard.setConfirmEnabled(true)
        } else {
            binding.keyboard.setConfirmEnabled(false)
        }
    }

}