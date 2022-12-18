package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputCustomNameBinding
import com.architecture.light.domain.transaction.action.ActionInputCustomName
import com.architecture.light.utils.KeyBoardUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputCustomNameActivity : AppActivityForAction() {

    private val binding: ActivityInputCustomNameBinding by lazy {
        ActivityInputCustomNameBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.btConfirm.click { clickConfirm() }
        InputTextManager.with(this)
            .addView(binding.etName)
            .setMain(binding.btConfirm)
            .build()
        KeyBoardUtils.addLayoutListener(binding.layoutBottom, binding.btConfirm)
    }

    private fun clickConfirm() {
        val name = binding.etName.text.toString()
        if (name.valid) {
            val info = ActionInputCustomName.Info(name)
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
    }

}