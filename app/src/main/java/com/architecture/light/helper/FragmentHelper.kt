package com.architecture.light.helper

import com.android.architecture.ui.page.action.FragmentAction
import com.architecture.light.R

/**
 * Created by SuQi on 2022/10/16.
 * Describe:
 */
object FragmentHelper {

    fun getAnim(): FragmentAction.Anim {
        return FragmentAction.Anim(
            R.anim.fragment_right_in,
            R.anim.fragment_left_out,
            R.anim.fragment_left_in,
            R.anim.fragment_right_out
        )
    }

}