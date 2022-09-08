package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityChoosePaymentMethodBinding
import com.architecture.light.domain.transaction.action.UIParams

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
        val transData = intent.getParcelableExtra<TransData>(UIParams.TRANS_DATA)!!

        binding.btCancel.click {
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
        binding.btConfirm.click {
            finish(ActionResult(ErrorCode.SUCCESS))
        }
    }

}