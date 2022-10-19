package com.architecture.light.domain.transaction.action

import com.android.architecture.domain.navigation.ANavigationAction
import com.android.architecture.extension.openActivity
import com.architecture.light.domain.transaction.action.activity.ThirdActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionThirdActivity(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {
        activity.openActivity<ThirdActivity>()
    }

}