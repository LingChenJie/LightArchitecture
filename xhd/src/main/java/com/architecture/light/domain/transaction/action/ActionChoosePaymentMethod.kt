package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.transaction.action.activity.ChoosePaymentMethodActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionChoosePaymentMethod(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null
    private lateinit var transData: TransData

    fun setParam(activity: BaseActivity, transData: TransData) {
        this.activity = activity
        this.transData = transData
    }

    override fun onExecute() {
        activity!!.openActivity<ChoosePaymentMethodActivity> {
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    data class PaymentMethodInfo(
        val transactionPlatform: Int,
        val bankAccount: String,
        val bankName: String
    )

}