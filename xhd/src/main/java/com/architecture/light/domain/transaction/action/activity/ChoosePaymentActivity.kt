package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.utils.DoubleUtils
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.ActivityChoosePaymentBinding
import com.architecture.light.domain.transaction.action.ActionChoosePayment
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.helper.AmountHelper
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

    private val adapter by lazy { ChoosePaymentAdapter(this) }

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
        adapter.setItemChangeListener(object : ChoosePaymentAdapter.ItemChangeListener {
            override fun change() {
                refreshUI(room)
            }
        })
        binding.btConfirm.click {
            val paymentInfo =
                ActionChoosePayment.PaymentInfo(
                    totalAmount,
                    unPaidAmount,
                    thisPaidAmount,
                    transData.searchRoomResponse!!
                )
            finish(ActionResult(ErrorCode.SUCCESS, paymentInfo))
        }
        binding.btConfirm.isEnabled = false
    }

    var totalAmount = 0.0
    var unPaidAmount = 0.0
    var thisPaidAmount = 0.0

    private fun refreshUI(selectRoom: SearchRoomResponse.Data) {
        totalAmount = 0.0
        unPaidAmount = 0.0
        thisPaidAmount = 0.0
        val feeList = selectRoom.feeList
        for (fee in feeList) {
            if (fee.isChecked) {
                totalAmount = DoubleUtils.add(totalAmount, fee.amount)
                unPaidAmount = DoubleUtils.add(unPaidAmount, fee.yeAmount)
                thisPaidAmount = DoubleUtils.add(thisPaidAmount, fee.paymentAmount)
            }
        }
        binding.tvTotalAmountPayment.text = AmountHelper.formatAmount(totalAmount)
        binding.tvTotalAmountUnpaid.text = AmountHelper.formatAmount(unPaidAmount)
        binding.tvTotalAmountThisPaid.text = AmountHelper.formatAmount(thisPaidAmount)
        binding.btConfirm.isEnabled = thisPaidAmount > 0
    }

}