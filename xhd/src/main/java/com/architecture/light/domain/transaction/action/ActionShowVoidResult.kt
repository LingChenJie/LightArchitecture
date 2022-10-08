package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.openActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.transaction.action.activity.ShowVoidResultActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionShowVoidResult(listener: ActionStartListener) : AAction(listener) {

    private var actionResult: ActionResult? = null
    private var transData: TransData? = null

    fun setParam(actionResult: ActionResult, transData: TransData) {
        this.actionResult = actionResult
        this.transData = transData
    }

    override fun onExecute() {
        activity.openActivity<ShowVoidResultActivity> {
            putExtra(UIParams.ACTION_RESULT, actionResult)
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

}