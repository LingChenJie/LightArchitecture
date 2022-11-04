package com.architecture.light.ui.page.nested.scrolling

import com.android.architecture.extension.binding
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedTraditionBinding

/**
 * Created by SuQi on 2022/11/2.
 * Describe:使用传统机制来实现嵌套滑动
 */
class NestedTraditionActivity : AppActivity() {

    private val binding: ActivityNestedTraditionBinding by binding()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
        val adapter: FragmentPagerAdapter<TabFragment> = FragmentPagerAdapter(this)
        adapter.addFragment(TabFragment.newInstance("传统事件分发机制嵌套滑动"), "首页")
        adapter.addFragment(TabFragment.newInstance("传统事件分发机制嵌套滑动"), "全部")
        adapter.addFragment(TabFragment.newInstance("传统事件分发机制嵌套滑动"), "作者")
        adapter.addFragment(TabFragment.newInstance("传统事件分发机制嵌套滑动"), "专辑")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


}