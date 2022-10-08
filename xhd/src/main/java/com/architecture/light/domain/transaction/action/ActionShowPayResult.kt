package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.openActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.transaction.action.activity.ShowPayResultActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionShowPayResult(listener: ActionStartListener) : AAction(listener) {

    private lateinit var titleName: String
    private lateinit var actionResult: ActionResult
    private lateinit var transData: TransData

    fun setParam(
        titleName: String,
        actionResult: ActionResult,
        transData: TransData,
    ) {
        this.titleName = titleName
        this.actionResult = actionResult
        this.transData = transData
    }

    override fun onExecute() {
        activity.openActivity<ShowPayResultActivity> {
            putExtra(UIParams.TITLE_NAME, titleName)
            putExtra(UIParams.ACTION_RESULT, actionResult)
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

}