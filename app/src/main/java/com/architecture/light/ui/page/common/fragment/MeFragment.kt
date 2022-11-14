package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentMeBinding
import com.architecture.light.ui.page.common.CommonActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class MeFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance() = MeFragment()
    }

    private val binding: FragmentMeBinding by binding()

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        binding.titleView.apply {
            backView.visibility = View.GONE
        }
        binding.btnMineDialog.click {

        }
        binding.btnMineHint.click {

        }
    }

}