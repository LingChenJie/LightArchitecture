package com.architecture.light.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentBrowserBinding
import com.architecture.light.ui.page.activity.CommonActivity

/**
 * Created by SuQi on 2022/8/21.
 * Describe:
 */
class BrowserFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): BrowserFragment {
            return BrowserFragment()
        }
    }

    private lateinit var binding: FragmentBrowserBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }
}