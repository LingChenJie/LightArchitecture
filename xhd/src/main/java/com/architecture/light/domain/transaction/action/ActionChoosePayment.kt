package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
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

    private var activity: BaseActivity? = null
    private lateinit var transData: TransData

    fun setParam(activity: BaseActivity, transData: TransData) {
        this.activity = activity
        this.transData = transData
    }

    override fun onExecute() {
        activity!!.openActivity<ChoosePaymentActivity> {
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    data class PaymentInfo(val amount: Long, val searchRoomResponse: SearchRoomResponse)

}