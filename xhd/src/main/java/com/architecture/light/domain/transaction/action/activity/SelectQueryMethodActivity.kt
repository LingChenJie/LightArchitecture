package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.databinding.ActivitySelectQueryMethodBinding
import com.architecture.light.domain.transaction.action.ActionSelectQueryMethod

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class SelectQueryMethodActivity : AppActivityForAction() {

    private val binding: ActivitySelectQueryMethodBinding by lazy {
        ActivitySelectQueryMethodBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.layoutIdcardQuery.click {
            finish(ActionResult(ErrorCode.SUCCESS, ActionSelectQueryMethod.QueryMethod.IdCard))
        }
        binding.layoutPhoneQuery.click {
            finish(ActionResult(ErrorCode.SUCCESS, ActionSelectQueryMethod.QueryMethod.Tel))
        }
        binding.layoutRoominfoQuery.click {
            finish(ActionResult(ErrorCode.SUCCESS, ActionSelectQueryMethod.QueryMethod.RoomInfo))
        }
    }

    override fun clickBack() {
        finish(ActionResult(AppErrorCode.BACK_TO_PREVIOUS_PAGE))
    }

}