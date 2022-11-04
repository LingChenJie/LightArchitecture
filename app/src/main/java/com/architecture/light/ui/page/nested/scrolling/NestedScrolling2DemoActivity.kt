package com.architecture.light.ui.page.nested.scrolling

import com.android.architecture.extension.binding
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedScrolling2DemoBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/4
 * Modify date: 2022/11/4
 * Version: 1
 */
class NestedScrolling2DemoActivity : AppActivity() {

    private val binding: ActivityNestedScrolling2DemoBinding by binding()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
        val adapter: FragmentPagerAdapter<TabFragment> = FragmentPagerAdapter(this)
        adapter.addFragment(TabFragment.newInstance("NestedScrolling2"), "首页")
        adapter.addFragment(TabFragment.newInstance("NestedScrolling2"), "全部")
        adapter.addFragment(TabFragment.newInstance("NestedScrolling2"), "作者")
        adapter.addFragment(TabFragment.newInstance("NestedScrolling2"), "专辑")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}