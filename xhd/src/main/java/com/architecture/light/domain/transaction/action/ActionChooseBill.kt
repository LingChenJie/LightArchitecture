package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.domain.transaction.action.activity.ChooseBillActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionChooseBill(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null
    private lateinit var transData: TransData
    private lateinit var titleName: String

    fun setParam(activity: BaseActivity, transData: TransData, titleName: String) {
        this.activity = activity
        this.transData = transData
        this.titleName = titleName
    }

    override fun onExecute() {
        activity!!.openActivity<ChooseBillActivity> {
            putExtra(UIParams.TRANS_DATA, transData)
            putExtra(UIParams.TITLE_NAME, titleName)
        }
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    data class Info(
        val searchBillResponse: SearchBillResponse,
    )

}