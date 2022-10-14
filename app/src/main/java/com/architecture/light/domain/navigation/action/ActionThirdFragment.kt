package com.architecture.light.domain.navigation.action

import com.android.architecture.domain.navigation.ANavigationAction
import com.architecture.light.domain.navigation.action.fragment.ThirdFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionThirdFragment(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {
        val fragment = ThirdFragment.newInstance()
        if (activity.findFragmentByTag(fragment.getTagName()) == null) {
            activity.addFragmentToStack(fragment)
        } else {
            activity.removeFragmentOnStackUtilSelf(fragment)
        }
    }

    override fun getActivity(): NavigationActivity {
        return super.getActivity() as NavigationActivity
    }

}