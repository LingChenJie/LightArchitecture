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

    private val backStack = ArrayDeque<AppFragmentForNavigationAction>()

    /**
     * 添加Fragment到堆栈顶部
     */
    fun addFragment(fragment: AppFragmentForNavigationAction) {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return
        }
        if (backStack.size > 0) {
            val hideFrag = backStack.peekLast()
            backStack.add(fragment)
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_in,
                    R.anim.fragment_left_out,
                    R.anim.fragment_left_in,
                    R.anim.fragment_right_out
                )
                .hide(hideFrag!!)
                .add(binding.container.id, fragment, fragment.getTagName())
                .addToBackStack(fragment.getTagName())
                .commit()
        } else {
            backStack.add(fragment)
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fragment_right_in,
                    R.anim.fragment_left_out,
                    R.anim.fragment_left_in,
                    R.anim.fragment_right_out
                )
                .add(binding.container.id, fragment, fragment.getTagName())
                .addToBackStack(fragment.getTagName())
                .commit()
        }
    }

    /**
     * 移除堆栈中的Fragment
     */
    fun removeTopFragmentUtilSelf(fragment: AppFragmentForNavigationAction) {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return
        }
        var previousFragment: AppFragmentForNavigationAction? = null
        val array = backStack.toArray()
        for (index in array.size - 1 downTo 0) {
            val temp = array[index] as AppFragmentForNavigationAction
            if (temp.getTagName() == fragment.getTagName()) {
                break
            }
            previousFragment = temp
        }
        if (previousFragment != null) {
            fragmentManager.popBackStack(
                previousFragment.getTagName(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        for (index in array.indices) {
            val peekLast = backStack.peekLast()
            if (peekLast.getTagName() == fragment.getTagName()) {
                break
            }
            backStack.removeLast()
            fragmentManager.fragments.removeAt(backStack.size)
        }
    }

    /**
     * 替换堆栈底部Fragment
     */
    fun replaceBottomFragment(fragment: AppFragmentForNavigationAction) {
        if (supportFragmentManager.isStateSaved) {
            return
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_right_in,
                R.anim.fragment_left_out,
                R.anim.fragment_left_in,
                R.anim.fragment_right_out
            )
            .replace(binding.container.id, fragment, fragment.getTagName())
            .commit()
    }

    /**
     * 查找堆栈中是否存在Fragment
     */
    fun findFragmentByTag(tagName: String): AppFragmentForNavigationAction? {
        val fragment = supportFragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            fragment as AppFragmentForNavigationAction
            return fragment
        }
        return null
    }

}