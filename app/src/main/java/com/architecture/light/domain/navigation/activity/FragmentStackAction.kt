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
     * 获取回退栈的FragmentManager
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
        val hasFragment = backStack.contains(fragment.getTagName())
        if (hasFragment) {
            fragmentManager.popBackStack(fragment.getTagName(), 0)
            for (item in backStack.reversed()) {
                if (fragment.getTagName() == item) {
                    break
                }
                backStack.removeLast()
                fragmentManager.fragments.removeAt(backStack.size)
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
     * 显示回退栈中的Fragment
     */
    fun showFragmentOnStack(fragment: AppFragmentForNavigationAction) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        val hasFragment = backStack.contains(fragment.getTagName())
        if (hasFragment) {
            val hideFrag = findShowFragment()!!
            val showFrag = findFragmentByTag(fragment.getTagName())!!
            if (fragment.getTagName() != hideFrag.getTagName()) {
                val showIndex = findStackIndex(fragment.getTagName())
                val hideIndex = findStackIndex(hideFrag.getTagName())
                val enterAnim: Int
                val exitAnim: Int
                if (showIndex > hideIndex) {
                    enterAnim = R.anim.fragment_right_in
                    exitAnim = R.anim.fragment_left_out
                } else {
                    enterAnim = R.anim.fragment_left_in
                    exitAnim = R.anim.fragment_right_out
                }
                fragmentManager.beginTransaction()
                    .setCustomAnimations(
                        enterAnim,
                        exitAnim,
                    )
                    .hide(hideFrag)
                    .show(showFrag)
                    .commit()
            }
        }
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
     * 查找当前正在显示的Fragment
     */
    fun findShowFragment(): AppFragmentForNavigationAction? {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        for (item in backStack) {
            val fragment = fragmentManager.findFragmentByTag(item)
            if (fragment != null && !fragment.isHidden) {
                fragment as AppFragmentForNavigationAction
                return fragment
            }
        }
        return null
    }

    /**
     * 根据tagName查找Fragment在栈中的位置
     */
    fun findStackIndex(tagName: String): Int {
        val backStack = getBackStack()
        for ((index, item) in backStack.withIndex()) {
            if (item == tagName) {
                return index
            }
        }
        return -1
    }

}