package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentMessageBinding
import com.architecture.light.ui.page.common.CommonActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class MessageFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    private val binding: FragmentMessageBinding by binding()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
    }

}