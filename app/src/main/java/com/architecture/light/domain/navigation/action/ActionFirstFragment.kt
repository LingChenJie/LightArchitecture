package com.architecture.light.domain.navigation.action

import android.content.Intent
import android.os.Bundle
import com.android.architecture.domain.navigation.ANavigationAction
import com.architecture.light.domain.navigation.action.fragment.FirstFragment
import com.architecture.light.domain.navigation.activity.NavigationActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ActionFirstFragment(listener: ActionStartListener) : ANavigationAction(listener) {

    override fun onExecute() {
        val fragment = FirstFragment.newInstance()
        val bundle = Bundle()
        bundle.putString("key", "suqi")
        fragment.arguments = bundle
        if (activity.findFragmentByTag(fragment.getTagName()) == null) {
            activity.addFragmentOnStack(fragment)
        } else {
            activity.backFragment(fragment)
//            activity.showFragmentOnStack(fragment)
        }
    }

    override fun getActivity(): NavigationActivity {
        return super.getActivity() as NavigationActivity
    }

}