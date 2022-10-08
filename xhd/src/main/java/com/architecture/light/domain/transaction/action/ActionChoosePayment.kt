package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.domain.transaction.action.activity.ChoosePaymentActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionChoosePayment(listener: ActionStartListener) : AAction(listener) {

    private lateinit var transData: TransData

    fun setParam(transData: TransData) {
        this.transData = transData
    }

    override fun onExecute() {
        activity.openActivity<ChoosePaymentActivity> {
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

    data class PaymentInfo(
        val totalAmount: Double,
        val unpaidAmount: Double,
        val amount: Double,
        val searchRoomResponse: SearchRoomResponse
    )

}