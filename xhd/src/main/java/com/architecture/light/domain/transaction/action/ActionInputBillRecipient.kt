package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.architecture.light.domain.transaction.action.activity.InputBillRecipientActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionInputBillRecipient(listener: ActionStartListener) : AAction(listener) {

    override fun onExecute() {
        activity.openActivity<InputBillRecipientActivity>()
    }

    class BillRecipientInfo(val account: String)

}