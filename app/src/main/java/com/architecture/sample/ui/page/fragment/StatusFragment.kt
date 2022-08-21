package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.sample.app.AppFragment
import com.architecture.sample.databinding.FragmentStatusBinding
import com.architecture.sample.ui.page.activity.CommonActivity

/**
 * Created by SuQi on 2022/8/21.
 * Describe:
 */
class StatusFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance(): StatusFragment {
            return StatusFragment()
        }
    }

    private lateinit var binding: FragmentStatusBinding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup): View {
        binding = FragmentStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }
}