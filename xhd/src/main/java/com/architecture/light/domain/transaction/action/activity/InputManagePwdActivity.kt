package com.architecture.light.domain.transaction.action.activity

import android.view.View
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.databinding.ActivityInputManagePwdBinding
import com.architecture.light.databinding.ActivityInputTelBinding
import com.architecture.light.databinding.ActivitySelectQueryMethodBinding
import com.architecture.light.domain.transaction.action.ActionInputTel
import com.architecture.light.domain.transaction.action.ActionSelectQueryMethod
import com.architecture.light.settings.AppCache
import com.architecture.light.ui.view.NumberKeyboardView
import com.architecture.light.ui.view.PasswordKeyboardView

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputManagePwdActivity : AppActivityForAction() {

    private val binding: ActivityInputManagePwdBinding by lazy {
        ActivityInputManagePwdBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.keyboard.bindEditText(binding.etPwd)
        binding.keyboard.setOnInputListener(object : PasswordKeyboardView.OnInputListener {
            override fun textChanged(password: String) {
                notifyConfirmEnabled()
            }

            override fun confirm(password: String) {
                clickConfirm(password)
            }
        })
        notifyConfirmEnabled()
    }

    private fun clickConfirm(password: String) {
        if (AppCache.getManagePwd() == password) {
            binding.tvErrorTip.visibility = View.GONE
            finish(ActionResult(ErrorCode.SUCCESS))
        } else {
            binding.tvErrorTip.visibility = View.VISIBLE
        }
    }

    private fun notifyConfirmEnabled() {
        val tel = binding.etPwd.text.toString()
        if (tel.valid && tel.length == AppCache.getManagePwd().length) {
            binding.keyboard.setConfirmEnabled(true)
        } else {
            binding.keyboard.setConfirmEnabled(false)
        }
    }

}