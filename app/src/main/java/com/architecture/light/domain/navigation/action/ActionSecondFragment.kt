package com.architecture.light.domain.navigation.action

import com.android.architecture.domain.navigation.ANavigationAction
import com.architecture.light.domain.navigation.action.fragment.SecondFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity
import com.architecture.light.helper.FragmentHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionSecondFragment(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {
        val fragment = SecondFragment.newInstance()
        if (activity.findFragmentByTag(fragment.tagName) == null) {
            activity.addFragmentOnStack(fragment, FragmentHelper.getAnim())
        } else {
            activity.backFragment(fragment)
        }
    }

    override fun getActivity(): NavigationActivity {
        return super.getActivity() as NavigationActivity
    }

}