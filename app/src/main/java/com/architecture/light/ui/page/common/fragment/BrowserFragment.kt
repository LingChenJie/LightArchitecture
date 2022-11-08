package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentBrowserBinding
import com.architecture.light.ui.page.common.CommonActivity

/**
 * Created by SuQi on 2022/8/21.
 * Describe:
 */
class BrowserFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance() = BrowserFragment()
    }

    private val binding: FragmentBrowserBinding by binding()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup): View {
        return binding.root
    }

    override fun initView() {
    }
}