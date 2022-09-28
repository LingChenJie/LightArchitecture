package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.domain.transaction.action.activity.InputAmountActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionInputAmount(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null
    private lateinit var titleName: String

    fun setParam(activity: BaseActivity?, titleName: String) {
        this.activity = activity
        this.titleName = titleName
    }

    override fun onExecute() {
        activity!!.openActivity<InputAmountActivity> {
            putExtra(UIParams.TITLE_NAME, titleName)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    class Info(val amount: Double)

}