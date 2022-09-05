package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.transaction.AAction
import com.android.architecture.extension.openActivity
import com.android.architecture.ui.page.BaseActivity
import com.architecture.light.domain.event.Messages
import com.architecture.light.domain.transaction.action.activity.InputLoginInfoActivity
import com.architecture.light.domain.transaction.action.activity.InputTelActivity
import com.architecture.light.domain.transaction.action.activity.ReadIdCardActivity
import com.architecture.light.domain.transaction.action.activity.SelectQueryMethodActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ActionInputTel(listener: ActionStartListener) : AAction(listener) {

    private var activity: BaseActivity? = null

    fun setParam(activity: BaseActivity?) {
        this.activity = activity
    }

    override fun onExecute() {
        activity!!.openActivity<InputTelActivity>()
    }

    override fun onClear() {
        super.onClear()
        activity = null
    }

    class TelInfo(val tel: String)

}