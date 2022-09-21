package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.Constant
import com.architecture.light.databinding.ActivityInputTelBinding
import com.architecture.light.domain.transaction.action.ActionInputTel
import com.architecture.light.ui.view.NumberKeyboardView

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputTelActivity : AppActivityForAction() {

    private val binding: ActivityInputTelBinding by lazy {
        ActivityInputTelBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.keyboard.bindEditText(binding.etTel)
        binding.keyboard.setOnInputListener(object : NumberKeyboardView.OnInputListener {
            override fun textChanged() {
                notifyConfirmEnabled()
            }

            override fun confirm() {
                clickConfirm()
            }
        })
        if (Constant.IS_DEBUG) {
            binding.etTel.setText("15212568170")
        }
        notifyConfirmEnabled()
    }

    private fun clickConfirm() {
        val tel = binding.etTel.text.toString()
        if (tel.valid) {
            val telInfo = ActionInputTel.TelInfo(tel)
            finish(ActionResult(ErrorCode.SUCCESS, telInfo))
        }
    }

    private fun notifyConfirmEnabled() {
        val tel = binding.etTel.text.toString()
//        if (tel.valid && tel.length == 11) {
//            binding.keyboard.setConfirmEnabled(true)
//        } else {
//            binding.keyboard.setConfirmEnabled(false)
//        }
    }

}