package com.architecture.light.domain.navigation.activity

import androidx.fragment.app.FragmentManager
import com.architecture.light.R
import com.architecture.light.app.AppFragmentForNavigationAction
import java.util.*

/**
 * Created by SuQi on 2022/10/15.
 * Describe:
 */
interface FragmentStackAction {

    /**
     * 回退栈，管理Fragment的回退
     */
    fun getBackStack(): ArrayDeque<String>

    /**
     * 获取显示Fragment的布局Id
     */
    fun getStackFragmentId(): Int

    /**
     * 获取FragmentManager
     */
    fun getStackFragmentManager(): FragmentManager


    /**
     * 将Fragment添加到回退栈中
     */
    fun addFragmentOnStack(fragment: AppFragmentForNavigationAction) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        if (backStack.size > 0) {
            val hideFrag = findFragmentByTag(backStack.peekLast()!!)!!
            backStack.add(fragment.getTagName())
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_in,
                    R.anim.fragment_left_out,
                    R.anim.fragment_left_in,
                    R.anim.fragment_right_out
                )
                .hide(hideFrag)
                .add(getStackFragmentId(), fragment, fragment.getTagName())
                .addToBackStack(fragment.getTagName())
                .commit()
        } else {
            replaceFragmentOnStack(fragment)
        }
    }

    /**
     * 回退到指定Fragment
     */
    fun backFragment(fragment: AppFragmentForNavigationAction) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        val topTagName = findTopFragmentTagName(backStack, fragment.getTagName())
        if (topTagName != null) {
            fragmentManager.popBackStack(
                topTagName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            for (item in backStack.reversed()) {
                backStack.removeLast()
                fragmentManager.fragments.removeAt(backStack.size)
                if (topTagName == item) {
                    break
                }
            }
        }
    }

    /**
     * 替换回退栈的底部Fragment
     */
    fun replaceFragmentOnStack(fragment: AppFragmentForNavigationAction) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        backStack.clear()
        backStack.add(fragment.getTagName())
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_right_in,
                R.anim.fragment_left_out,
                R.anim.fragment_left_in,
                R.anim.fragment_right_out
            )
            .replace(getStackFragmentId(), fragment, fragment.getTagName())
            .addToBackStack(fragment.getTagName())
            .commit()
    }

    /**
     * 根据tagName查询回退栈中的Fragment
     */
    fun findFragmentByTag(tagName: String): AppFragmentForNavigationAction? {
        val fragmentManager = getStackFragmentManager()
        val fragment = fragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            fragment as AppFragmentForNavigationAction
            return fragment
        }
        return null
    }

    /**
     * 找到指定Fragment的上层FragmentTagName
     */
    private fun findTopFragmentTagName(
        backStack: ArrayDeque<String>,
        currentFragmentTagName: String
    ): String? {
        var topTagName: String? = null
        val array = backStack.toArray()
        for (item in array.reversed()) {
            val temp = item as String
            if (temp == currentFragmentTagName) {
                break
            }
            topTagName = temp
        }
        return topTagName
    }

}