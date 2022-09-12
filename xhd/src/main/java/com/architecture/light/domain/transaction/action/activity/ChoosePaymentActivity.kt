package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.ActivityChoosePaymentBinding
import com.architecture.light.domain.transaction.action.ActionChoosePayment
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ui.adapter.ChoosePaymentAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChoosePaymentActivity : AppActivityForAction() {

    private val binding: ActivityChoosePaymentBinding by lazy {
        ActivityChoosePaymentBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ChoosePaymentAdapter() }

    override fun initView() {
        setContentView(binding.root)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        val data = transData.searchRoomResponse!!.data
        var selectRoom: SearchRoomResponse.Data? = null
        for (room in data) {
            if (room.isChecked) {
                selectRoom = room
                break
            }
        }
        val room = selectRoom!!
        binding.tvCustomName.text = room.cstName
        binding.tvCustomPhone.text = room.tel
        binding.tvCustomCardId.text = room.cardID
        binding.tvRoomName.text = room.roomInfo
        adapter.setData(room.feeList)
        binding.recyclerView.adapter = adapter
        binding.btCancel.click {
            finish(ActionResult(AppErrorCode.BACK_TO_PREVIOUS_PAGE))
        }
        binding.btConfirm.click {
            //TODO
            val amount = 1L
            val paymentInfo =
                ActionChoosePayment.PaymentInfo(amount, transData.searchRoomResponse!!)
            finish(ActionResult(ErrorCode.SUCCESS, paymentInfo))
        }
    }

}