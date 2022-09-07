package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputTelBinding
import com.architecture.light.domain.transaction.action.ActionInputTel

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
        binding.btConfirm.click {
            val tel = binding.etTel.text.toString()
            if (tel.valid) {
                val telInfo = ActionInputTel.TelInfo(tel)
                finish(ActionResult(ErrorCode.SUCCESS, telInfo))
            }
        }
    }

}