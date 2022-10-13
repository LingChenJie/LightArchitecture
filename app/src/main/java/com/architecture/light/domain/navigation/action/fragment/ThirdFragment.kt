package com.architecture.light.domain.navigation.action.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppFragmentForNavigationAction
import com.architecture.light.databinding.Fragment3Binding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class ThirdFragment : AppFragmentForNavigationAction() {

    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    private lateinit var binding: Fragment3Binding

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = Fragment3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        binding.btTo1.click {
            finish(NavigationResult(ErrorCode.SUCCESS))
        }
    }

}