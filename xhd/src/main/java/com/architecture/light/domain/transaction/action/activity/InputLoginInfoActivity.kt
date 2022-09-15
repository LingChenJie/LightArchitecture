package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.helper.ClickHelper
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.databinding.ActivityInputLoginInfoBinding
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo
import com.architecture.light.ext.toast
import com.architecture.light.helper.PermissionsHelper
import com.architecture.light.utils.KeyBoardUtils
import com.hjq.permissions.Permission

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
        //binding.etUsername.setText("")
        binding.etPassword.setText("")
    }

    override fun initView() {
        setContentView(binding.root)
        binding.btLogin.click {
            if (ClickHelper.isDoubleClick()) {
                return@click
            }
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.empty || password.empty) {
                toast(getString(R.string.login_please_input_correct_username_password))
                return@click
            }
            PermissionsHelper.requirePermissions(Permission.WRITE_EXTERNAL_STORAGE) {
                val loginInfo = ActionInputLoginInfo.LoginInfo(username, password)
                finish(ActionResult(ErrorCode.SUCCESS, loginInfo))
            }
        }
        binding.etUsername.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) checkUsername()
        }
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) checkPassword()
        }
        InputTextManager.with(this)
            .addView(binding.etUsername)
            .addView(binding.etPassword)
            .setMain(binding.btLogin)
            .build()
        KeyBoardUtils.addLayoutListener(binding.layoutBottom, binding.btLogin)
        PermissionsHelper.requirePermissions(Permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun checkUsername() {
        binding.ivUsername.setImageResource(R.drawable.icon_login_username)
        binding.vUsername.setBackgroundColor(getColor(R.color.common_accent_color))
        binding.ivPassword.setImageResource(R.drawable.icon_login_password_un)
        binding.vPassword.setBackgroundColor(getColor(R.color.common_uncheck_color))
    }

    private fun checkPassword() {
        binding.ivUsername.setImageResource(R.drawable.icon_login_username_un)
        binding.vUsername.setBackgroundColor(getColor(R.color.common_uncheck_color))
        binding.ivPassword.setImageResource(R.drawable.icon_login_password)
        binding.vPassword.setBackgroundColor(getColor(R.color.common_accent_color))
    }

    override fun clickBack() {
        finish(ActionResult(AppErrorCode.EXIT_APP))
    }

}