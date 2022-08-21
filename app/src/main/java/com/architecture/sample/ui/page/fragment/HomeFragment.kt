package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.FragmentHomeBinding
import com.architecture.sample.ui.adapter.HomeTabAdapter
import com.architecture.sample.ui.page.activity.CommonActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class HomeFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var binding: FragmentHomeBinding
    private val pagerAdapter: FragmentPagerAdapter<AppFragment<*>> by lazy {
        FragmentPagerAdapter(this)
    }
    private val tabAdapter = HomeTabAdapter()

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        ImmersionBar.setTitleBar(this, binding.toolbar)
        return binding.root
    }

    override fun initView() {
        pagerAdapter.addFragment(StatusFragment.newInstance())
        pagerAdapter.addFragment(BrowserFragment.newInstance())
        binding.viewPager.adapter = pagerAdapter
        tabAdapter.addData(listOf("列表演示", "网页演示"))
        tabAdapter.setOnTabListener(object : HomeTabAdapter.OnTabListener {
            override fun onTabSelected(recyclerView: RecyclerView, position: Int) {
            }
        })
        binding.rvTab.adapter = tabAdapter
    }

}