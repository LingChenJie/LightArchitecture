package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.architecture.light.domain.transaction.action.activity.SelectQueryMethodActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionSelectQueryMethod(listener: ActionStartListener) : AAction(listener) {

    private lateinit var titleName: String
    private lateinit var queryMethodArray: Array<String>

    fun setParam(
        titleName: String,
        queryMethodArray: Array<String> = arrayOf(
            QueryMethod.IdCard.toString(),
            QueryMethod.Tel.toString(),
            QueryMethod.RoomInfo.toString()
        )
    ) {
        this.titleName = titleName
        this.queryMethodArray = queryMethodArray
    }

    override fun onExecute() {
        activity.openActivity<SelectQueryMethodActivity> {
            putExtra(UIParams.TITLE_NAME, titleName)
            putExtra(UIParams.QUERY_METHOD_ARRAY, queryMethodArray)
        }
    }

    sealed class QueryMethod {
        object IdCard : QueryMethod()
        object Tel : QueryMethod()
        object RoomInfo : QueryMethod()
    }

}