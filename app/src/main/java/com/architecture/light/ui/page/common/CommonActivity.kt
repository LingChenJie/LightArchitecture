package com.architecture.light.ui.page.common

import androidx.recyclerview.widget.GridLayoutManager
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.light.R
import com.architecture.light.app.AppActivity
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.ActivityCommonBinding
import com.architecture.light.ui.adapter.NavigationAdapter
import com.architecture.light.ui.page.common.fragment.FoundFragment
import com.architecture.light.ui.page.common.fragment.HomeFragment
import com.architecture.light.ui.page.common.fragment.MeFragment
import com.architecture.light.ui.page.common.fragment.MessageFragment

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

    private val pagerAdapter: FragmentPagerAdapter<AppFragment<CommonActivity>> =
        FragmentPagerAdapter(this)
    private val navigationAdapter = NavigationAdapter()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
        pagerAdapter.addFragment(HomeFragment.newInstance())
        pagerAdapter.addFragment(FoundFragment.newInstance())
        pagerAdapter.addFragment(MessageFragment.newInstance())
        pagerAdapter.addFragment(MeFragment.newInstance())
        binding.viewPager.adapter = pagerAdapter
        val data = listOf(
            NavigationAdapter.MenuItem(
                getString(R.string.common_nav_index),
                getDrawable(R.drawable.common_home_selector)!!
            ),
            NavigationAdapter.MenuItem(
                getString(R.string.common_nav_found),
                getDrawable(R.drawable.common_found_selector)!!
            ),
            NavigationAdapter.MenuItem(
                getString(R.string.common_nav_message),
                getDrawable(R.drawable.common_message_selector)!!
            ),
            NavigationAdapter.MenuItem(
                getString(R.string.common_nav_me),
                getDrawable(R.drawable.common_me_selector)!!
            )
        )
        navigationAdapter.setData(data)
        navigationAdapter.setOnNavigationListener { position ->
            binding.viewPager.currentItem = position
        }
        binding.rvNavigation.layoutManager = GridLayoutManager(this, 4)
        binding.rvNavigation.adapter = navigationAdapter
    }

}