package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.architecture.extension.binding
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentHomeBinding
import com.architecture.light.ui.adapter.HomeTabAdapter
import com.architecture.light.ui.page.common.CommonActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class HomeFragment : AppFragment<CommonActivity>(), HomeTabAdapter.OnTabListener,
    ViewPager.OnPageChangeListener {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private val binding: FragmentHomeBinding by binding()
    private val pagerAdapter: FragmentPagerAdapter<AppFragment<*>> by lazy {
        FragmentPagerAdapter(this)
    }
    private val tabAdapter = HomeTabAdapter()

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        ImmersionBar.setTitleBar(this, binding.toolbar)
        return binding.root
    }

    override fun initView() {
        pagerAdapter.addFragment(StatusFragment.newInstance(), "列表演示")
        pagerAdapter.addFragment(BrowserFragment.newInstance(), "网页演示")
        tabAdapter.addData(listOf("列表演示", "网页演示"))
        tabAdapter.setOnTabListener(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addOnPageChangeListener(this)
        binding.rvTab.adapter = tabAdapter
    }

    override fun onTabSelected(recyclerView: RecyclerView, position: Int) {
        binding.viewPager.currentItem = position
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tabAdapter.setSelectedPosition(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}