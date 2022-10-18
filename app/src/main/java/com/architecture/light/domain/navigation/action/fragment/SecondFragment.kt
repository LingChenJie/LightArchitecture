package com.architecture.light.domain.navigation.action.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.architecture.light.app.AppFragmentForNavigationAction
import com.architecture.light.databinding.Fragment2Binding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class SecondFragment : AppFragmentForNavigationAction() {

    companion object {
        fun newInstance(): SecondFragment {
            return SecondFragment()
        }
    }

    private val binding: Fragment2Binding by binding()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        binding.btTo3.click {
            finish(NavigationResult(ErrorCode.SUCCESS))
        }
    }

}