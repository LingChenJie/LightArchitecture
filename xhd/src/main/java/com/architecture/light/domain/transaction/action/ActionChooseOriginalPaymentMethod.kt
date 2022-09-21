package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.domain.transaction.action.activity.ChooseOriginalPaymentMethodActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionChooseOriginalPaymentMethod(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null

    fun setParam(activity: BaseActivity) {
        this.activity = activity
    }

    override fun onExecute() {
        activity!!.openActivity<ChooseOriginalPaymentMethodActivity>()
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    data class PaymentMethodInfo(
        val transactionPlatform: Int,
    )

}