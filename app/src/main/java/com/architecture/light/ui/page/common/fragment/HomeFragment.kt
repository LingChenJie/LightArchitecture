package com.architecture.light.ui.page.common.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.architecture.extension.binding
import com.android.architecture.extension.getColor
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.android.architecture.ui.widget.layout.XCollapsingToolbarLayout
import com.architecture.light.R
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
    ViewPager.OnPageChangeListener, XCollapsingToolbarLayout.OnScrimsListener {

    companion object {
        fun newInstance() = HomeFragment()
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
        binding.collapsingToolbarLayout.setOnScrimsListener(this)
        pagerAdapter.addFragment(StatusFragment.newInstance(), "列表演示")
        pagerAdapter.addFragment(
            BrowserFragment.newInstance("https://github.com/LingChenJie"),
            "网页演示"
        )
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addOnPageChangeListener(this)
        binding.rvTab.adapter = tabAdapter
    }

    override fun initData() {
        tabAdapter.addData(listOf("列表演示", "网页演示"))
        tabAdapter.setOnTabListener(this)
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

    @SuppressLint("RestrictedApi")
    override fun onScrimsStateChange(layout: XCollapsingToolbarLayout, shown: Boolean) {
        getStatusBarConfig().statusBarDarkFont(shown).init()
        binding.tvAddress.setTextColor(
            ContextCompat.getColor(
                mActivity,
                if (shown) com.android.architecture.R.color.black else com.android.architecture.R.color.white
            )
        )
        binding.tvHint.setBackgroundResource(if (shown) R.drawable.home_search_bar_gray_bg else R.drawable.home_search_bar_transparent_bg)
        binding.tvHint.setTextColor(getColor(if (shown) com.android.architecture.R.color.black60 else com.android.architecture.R.color.white60))
        binding.ivHomeSearch.supportImageTintList =
            ColorStateList.valueOf(getColor(if (shown) R.color.common_icon_color else com.android.architecture.R.color.white))
    }

}