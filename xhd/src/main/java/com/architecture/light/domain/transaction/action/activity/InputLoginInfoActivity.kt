package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.extension.toast
import com.android.architecture.helper.ClickHelper
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.databinding.ActivityInputLoginInfoBinding
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputLoginInfoActivity : AppActivityForAction() {

    private val binding: ActivityInputLoginInfoBinding by lazy {
        ActivityInputLoginInfoBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        binding.etAccount.setText("")
        binding.etPassword.setText("")
    }

    override fun initView() {
        setContentView(binding.root)
        binding.btLogin.click {
            if (ClickHelper.isDoubleClick()) {
                return@click
            }
            val account = binding.etAccount.text.toString()// shengxy
            val password = binding.etPassword.text.toString()// 2
            if (account.empty || password.empty) {
                toast(getString(R.string.login_please_input_correct_username_password))
                return@click
            }
            val loginInfo = ActionInputLoginInfo.LoginInfo(account, password)
            finish(ActionResult(ErrorCode.SUCCESS, loginInfo))
        }
    }

    override fun clickBack() {
        finish(ActionResult(AppErrorCode.EXIT_APP))
    }

}