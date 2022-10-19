package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.navigation.ANavigationAction
import com.android.architecture.extension.openActivity
import com.android.architecture.extension.set
import com.architecture.light.domain.transaction.action.activity.FirstActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionFirstActivity(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {
        activity.openActivity<FirstActivity> {
            this["name"] = "zhangsan"
        }
    }

}