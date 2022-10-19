package com.architecture.light.domain.navigation.action

import android.os.Bundle
import com.android.architecture.domain.navigation.ANavigationAction
import com.android.architecture.extension.set
import com.architecture.light.domain.navigation.action.fragment.FirstFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity
import com.architecture.light.helper.FragmentHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionFirstFragment(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {

        val fragment = FirstFragment.newInstance().apply {
            arguments = Bundle().apply {
                this["name"] = "zhangsan"
            }
        }
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