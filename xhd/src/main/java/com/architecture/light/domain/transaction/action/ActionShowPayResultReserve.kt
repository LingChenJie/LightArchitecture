package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.domain.transaction.action.activity.ShowPayResultReserveActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionShowPayResultReserve(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null
    private var actionResult: ActionResult? = null
    private var transData: TransData? = null

    fun setParam(actionResult: ActionResult, transData: TransData, activity: BaseActivity) {
        this.actionResult = actionResult
        this.transData = transData
        this.activity = activity
    }

    override fun onExecute() {
        activity!!.openActivity<ShowPayResultReserveActivity> {
            putExtra(UIParams.ACTION_RESULT, actionResult)
            putExtra(UIParams.TRANS_DATA, transData)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    class TelInfo(val tel: String)

}