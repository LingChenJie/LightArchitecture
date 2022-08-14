package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.FragmentFoundBinding
import com.architecture.sample.databinding.FragmentHomeBinding
import com.architecture.sample.ui.page.activity.CommonActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class FoundFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): FoundFragment {
            return FoundFragment()
        }
    }

    private lateinit var binding: FragmentFoundBinding

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFoundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }

}