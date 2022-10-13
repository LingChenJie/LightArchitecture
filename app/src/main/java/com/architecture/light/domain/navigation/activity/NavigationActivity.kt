package com.architecture.light.domain.navigation.activity

import com.architecture.light.app.AppActivityForNavigationAction
import com.architecture.light.app.AppFragmentForNavigationAction
import com.architecture.light.databinding.ActivityNavigationBinding

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

    fun replaceFragment(fragment: AppFragmentForNavigationAction) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment, fragment.getTagName())
            .commit()
    }

    fun findFragment(tagName: String): AppFragmentForNavigationAction? {
        val fragment = supportFragmentManager.findFragmentByTag(tagName)
        if (fragment != null) {
            fragment as AppFragmentForNavigationAction
            return fragment
        }
        return null
    }

}