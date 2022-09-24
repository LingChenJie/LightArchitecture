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
    private lateinit var titleName: String
    private lateinit var transData: TransData
    private var showUnpaidAmount = true

    fun setParam(
        activity: BaseActivity,
        titleName: String,
        transData: TransData,
        showUnpaidAmount: Boolean = true
    ) {
        this.activity = activity
        this.titleName = titleName
        this.transData = transData
        this.showUnpaidAmount = showUnpaidAmount
    }

    override fun onExecute() {
        activity!!.openActivity<ChoosePaymentMethodActivity> {
            putExtra(UIParams.TITLE_NAME, titleName)
            putExtra(UIParams.TRANS_DATA, transData)
            putExtra(UIParams.SHOW_UNPAID_AMOUNT, showUnpaidAmount)
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