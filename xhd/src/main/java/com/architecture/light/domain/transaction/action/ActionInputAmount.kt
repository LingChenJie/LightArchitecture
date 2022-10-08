package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.architecture.light.domain.transaction.action.activity.InputAmountActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionInputAmount(listener: ActionStartListener) : AAction(listener) {

    private lateinit var titleName: String

    fun setParam(titleName: String) {
        this.titleName = titleName
    }

    override fun onExecute() {
        activity.openActivity<InputAmountActivity> {
            putExtra(UIParams.TITLE_NAME, titleName)
        }
    }

    class Info(val amount: Double)

}