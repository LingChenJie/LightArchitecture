package com.light.xhd.ui.page.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.light.xhd.app.AppActivity
import com.architecture.sample.app.AppFragment
import com.light.xhd.ui.adapter.NavigationAdapter
import com.light.xhd.ui.page.fragment.FoundFragment
import com.light.xhd.ui.page.fragment.HomeFragment
import com.light.xhd.ui.page.fragment.MeFragment
import com.light.xhd.ui.page.fragment.MessageFragment
import com.light.xhd.R
import com.light.xhd.databinding.ActivityCommonBinding

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