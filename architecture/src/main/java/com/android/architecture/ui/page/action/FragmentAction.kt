package com.android.architecture.ui.page.action

import androidx.fragment.app.FragmentManager
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.ui.page.BaseFragment
import java.util.*

/**
 * Created by SuQi on 2022/10/15.
 * Describe:
 */
interface FragmentAction {

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
    fun addFragmentOnStack(fragment: BaseFragment<out BaseActivity>, anim: Anim? = null) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        if (backStack.size > 0) {
            val hideFrag = findFragmentByTag(backStack.peekLast()!!)!!
            backStack.add(fragment.tagName)
            val beginTransaction = fragmentManager.beginTransaction()
            anim?.let {
                beginTransaction.setCustomAnimations(
                    it.enter,
                    it.exit,
                    it.popEnter,
                    it.popExit,
                )
            }
            beginTransaction
                .hide(hideFrag)
                .add(getStackFragmentId(), fragment, fragment.tagName)
                .addToBackStack(fragment.tagName)
                .commit()
        } else {
            replaceFragmentOnStack(fragment)
        }
    }

    /**
     * 回退到指定Fragment
     */
    fun backFragment(fragment: BaseFragment<out BaseActivity>) {
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
    fun replaceFragmentOnStack(fragment: BaseFragment<out BaseActivity>, anim: Anim? = null) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        backStack.clear()
        backStack.add(fragment.tagName)
        val beginTransaction = fragmentManager.beginTransaction()
        anim?.let {
            beginTransaction.setCustomAnimations(
                it.enter,
                it.exit,
                it.popEnter,
                it.popExit,
            )
        }
        beginTransaction
            .replace(getStackFragmentId(), fragment, fragment.tagName)
            .addToBackStack(fragment.tagName)
            .commit()
    }

    /**
     * 显示回退栈中的Fragment
     */
    fun showFragmentOnStack(fragment: BaseFragment<out BaseActivity>, anim: Anim? = null) {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        if (fragmentManager.isStateSaved) {
            return
        }
        val hasFragment = backStack.contains(fragment.tagName)
        if (hasFragment) {
            val hideFrag = findShowFragment()!!
            val showFrag = findFragmentByTag(fragment.tagName)!!
            if (fragment.tagName != hideFrag.tagName) {
                val showIndex = findStackIndex(fragment.tagName)
                val hideIndex = findStackIndex(hideFrag.tagName)
                val beginTransaction = fragmentManager.beginTransaction()
                anim?.let {
                    val enterAnim: Int
                    val exitAnim: Int
                    if (showIndex > hideIndex) {
                        enterAnim = it.enter
                        exitAnim = it.exit
                    } else {
                        enterAnim = it.popEnter
                        exitAnim = it.popExit
                    }
                    beginTransaction.setCustomAnimations(enterAnim, exitAnim)
                }
                beginTransaction
                    .hide(hideFrag)
                    .show(showFrag)
                    .commit()
            }
        }
    }

    /**
     * 根据tagName查询回退栈中的Fragment
     */
    fun findFragmentByTag(tagName: String): BaseFragment<out BaseActivity>? {
        val fragmentManager = getStackFragmentManager()
        val fragment = fragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            fragment as BaseFragment<out BaseActivity>
            return fragment
        }
        return null
    }

    /**
     * 查找当前正在显示的Fragment
     */
    fun findShowFragment(): BaseFragment<out BaseActivity>? {
        val backStack = getBackStack()
        val fragmentManager = getStackFragmentManager()
        for (item in backStack) {
            val fragment = fragmentManager.findFragmentByTag(item)
            if (fragment != null && !fragment.isHidden) {
                fragment as BaseFragment<out BaseActivity>
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

    class Anim(
        val enter: Int,
        val exit: Int,
        val popEnter: Int,
        val popExit: Int
    )

}