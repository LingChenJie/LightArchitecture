package com.architecture.light.domain.navigation.action.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.architecture.light.app.AppFragmentForNavigationAction
import com.architecture.light.databinding.Fragment1Binding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class FirstFragment : AppFragmentForNavigationAction() {

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    private val binding: Fragment1Binding by binding()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        binding.btTo2.click {
            finish(NavigationResult(ErrorCode.SUCCESS))
        }
    }

}