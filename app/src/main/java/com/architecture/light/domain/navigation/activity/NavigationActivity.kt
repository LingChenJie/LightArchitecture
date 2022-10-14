package com.architecture.light.domain.navigation.activity

import androidx.fragment.app.FragmentManager
import com.architecture.light.R
import com.architecture.light.app.AppActivityForNavigationAction
import com.architecture.light.app.AppFragmentForNavigationAction
import com.architecture.light.databinding.ActivityNavigationBinding
import java.util.*

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class NavigationActivity : AppActivityForNavigationAction() {

    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
    }

    private fun getFragmentLayoutId(): Int {
        return binding.container.id
    }

    private val backStack = ArrayDeque<AppFragmentForNavigationAction>()

    /**
     * 将Fragment添加到回退栈中
     */
    fun addFragmentToStack(fragment: AppFragmentForNavigationAction) {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return
        }
        if (backStack.size > 0) {
            val hideFrag = backStack.peekLast()!!
            backStack.add(fragment)
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_in,
                    R.anim.fragment_left_out,
                    R.anim.fragment_left_in,
                    R.anim.fragment_right_out
                )
                .hide(hideFrag)
                .add(getFragmentLayoutId(), fragment, fragment.getTagName())
                .addToBackStack(fragment.getTagName())
                .commit()
        } else {
            replaceFragmentOnStack(fragment)
        }
    }

    /**
     * 移除回退栈的顶部Fragment
     */
    fun removeFragmentOnStackUtilSelf(fragment: AppFragmentForNavigationAction) {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return
        }
        val topFragment = findTopFragment(backStack, fragment)
        if (topFragment != null) {
            fragmentManager.popBackStack(
                topFragment.getTagName(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            for (index in backStack.indices) {
                backStack.removeLast()
                fragmentManager.fragments.removeAt(backStack.size)
                if (topFragment.getTagName() == fragment.getTagName()) {
                    break
                }
            }
        }
    }

    /**
     * 替换回退栈的底部Fragment
     */
    fun replaceFragmentOnStack(fragment: AppFragmentForNavigationAction) {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return
        }
        backStack.add(fragment)
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_right_in,
                R.anim.fragment_left_out,
                R.anim.fragment_left_in,
                R.anim.fragment_right_out
            )
            .replace(getFragmentLayoutId(), fragment, fragment.getTagName())
            .addToBackStack(fragment.getTagName())
            .commit()
    }

    /**
     * 根据tagName查询回退栈中的Fragment
     */
    fun findFragmentByTag(tagName: String): AppFragmentForNavigationAction? {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            fragment as AppFragmentForNavigationAction
            return fragment
        }
        return null
    }

    private fun findTopFragment(
        backStack: ArrayDeque<AppFragmentForNavigationAction>,
        fragment: AppFragmentForNavigationAction
    ): AppFragmentForNavigationAction? {
        val array = backStack.toArray()
        for (item in array.reversed()) {
            val temp = item as AppFragmentForNavigationAction
            if (temp.getTagName() == fragment.getTagName()) {
                break
            }
            return temp
        }
        return null
    }

}