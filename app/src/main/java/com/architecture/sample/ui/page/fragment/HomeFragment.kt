package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.FragmentHomeBinding
import com.architecture.sample.ui.page.activity.CommonActivity

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
    private val pagerAdapter: FragmentPagerAdapter<AppFragment<*>> =
        FragmentPagerAdapter(this)

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        pagerAdapter.addFragment()
        pagerAdapter.addFragment()
        binding.viewPager.adapter = pagerAdapter

    }

}