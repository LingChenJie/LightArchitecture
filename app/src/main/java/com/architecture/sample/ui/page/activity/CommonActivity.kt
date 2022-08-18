package com.architecture.sample.ui.page.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.android.architecture.ui.adapter.FragmentPagerAdapter
import com.architecture.sample.R
import com.architecture.sample.app.AppActivity
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.ActivityCommonBinding
import com.architecture.sample.ui.adapter.NavigationAdapter
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