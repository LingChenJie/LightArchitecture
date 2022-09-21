package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.domain.event.Messages
import com.architecture.light.domain.transaction.action.activity.InputLoginInfoActivity
import com.architecture.light.domain.transaction.action.activity.SelectQueryMethodActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionSelectQueryMethod(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null
    private var queryMethodArray: Array<String>? = null

    fun setParam(
        activity: BaseActivity?,
        queryMethodArray: Array<String> = arrayOf(
            QueryMethod.IdCard.toString(),
            QueryMethod.Tel.toString(),
            QueryMethod.RoomInfo.toString()
        )
    ) {
        this.activity = activity
        this.queryMethodArray = queryMethodArray
    }

    override fun onExecute() {
        activity!!.openActivity<SelectQueryMethodActivity> {
            putExtra(UIParams.QUERY_METHOD_ARRAY, queryMethodArray)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    sealed class QueryMethod {
        object IdCard : QueryMethod()
        object Tel : QueryMethod()
        object RoomInfo : QueryMethod()
    }

}