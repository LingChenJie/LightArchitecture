package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.valid
import com.android.architecture.helper.DelayHelper
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputRoomInfoBinding
import com.architecture.light.domain.transaction.action.ActionInputRoomInfo
import com.architecture.light.utils.KeyBoardUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputRoomInfoActivity : AppActivityForAction() {

    private val binding: ActivityInputRoomInfoBinding by lazy {
        ActivityInputRoomInfoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.btConfirm.click {
            val roomInfo = binding.etRoomInfo.text.toString()
            if (roomInfo.valid) {
                val room = ActionInputRoomInfo.RoomInfo(roomInfo)
                finish(ActionResult(ErrorCode.SUCCESS, room))
            }
        }
        InputTextManager.with(this)
            .addView(binding.etRoomInfo)
            .setMain(binding.btConfirm)
            .build()
        KeyBoardUtils.addLayoutListener(binding.layoutBottom, binding.btConfirm)
    }

}