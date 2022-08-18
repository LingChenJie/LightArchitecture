package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.FragmentHomeBinding
import com.architecture.sample.databinding.FragmentMeBinding
import com.architecture.sample.ui.page.activity.CommonActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class MeFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }

    private lateinit var binding: FragmentMeBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }

}