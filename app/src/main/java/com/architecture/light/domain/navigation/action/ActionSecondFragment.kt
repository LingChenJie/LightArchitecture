package com.architecture.light.domain.navigation.action

import com.android.architecture.domain.navigation.ANavigationAction
import com.architecture.light.domain.navigation.action.fragment.SecondFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity

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
        if (activity.findFragment(fragment.getTagName()) == null) {
            activity.replaceFragment(fragment)
        }
    }

    override fun getActivity(): NavigationActivity {
        return super.getActivity() as NavigationActivity
    }

}