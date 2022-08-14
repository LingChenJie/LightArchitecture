package com.architecture.sample.ui.page.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.sample.R
import com.architecture.sample.app.AppActivity
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.ActivityCommonBinding
import com.architecture.sample.ui.adapter.CommonNavigationAdapter
import com.architecture.sample.ui.page.fragment.FoundFragment
import com.architecture.sample.ui.page.fragment.HomeFragment
import com.architecture.sample.ui.page.fragment.MeFragment
import com.architecture.sample.ui.page.fragment.MessageFragment

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class CommonActivity : AppActivity() {

    private val binding: ActivityCommonBinding by lazy {
        ActivityCommonBinding.inflate(layoutInflater)
    }

    private val mPagerAdapter: FragmentPagerAdapter<AppFragment<CommonActivity>> by lazy {
        FragmentPagerAdapter(this)
    }
    private val mNavigationAdapter: CommonNavigationAdapter by lazy { CommonNavigationAdapter() }

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
        mPagerAdapter.addFragment(HomeFragment.newInstance())
        mPagerAdapter.addFragment(FoundFragment.newInstance())
        mPagerAdapter.addFragment(MessageFragment.newInstance())
        mPagerAdapter.addFragment(MeFragment.newInstance())
        binding.viewPager.adapter = mPagerAdapter
        val data = listOf(
            CommonNavigationAdapter.MenuItem(
                getString(R.string.common_nav_index),
                getDrawable(R.drawable.common_home_selector)!!
            ),
            CommonNavigationAdapter.MenuItem(
                getString(R.string.common_nav_found),
                getDrawable(R.drawable.common_found_selector)!!
            ),
            CommonNavigationAdapter.MenuItem(
                getString(R.string.common_nav_message),
                getDrawable(R.drawable.common_message_selector)!!
            ),
            CommonNavigationAdapter.MenuItem(
                getString(R.string.common_nav_me),
                getDrawable(R.drawable.common_me_selector)!!
            )
        )
        mNavigationAdapter.setData(data)
        mNavigationAdapter.setOnNavigationListener { position ->
            binding.viewPager.currentItem = position
        }
        binding.rvNavigation.layoutManager = GridLayoutManager(this, 4)
        binding.rvNavigation.adapter = mNavigationAdapter
    }

}