package com.light.xhd.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.sample.app.AppFragment
import com.light.xhd.databinding.FragmentMessageBinding
import com.light.xhd.ui.page.activity.CommonActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class MessageFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): MessageFragment {
            return MessageFragment()
        }
    }

    private lateinit var binding: FragmentMessageBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }

}