package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.android.architecture.extension.toast
import com.architecture.light.R
import com.architecture.light.app.AppFragment
import com.architecture.light.config.glide.GlideApp
import com.architecture.light.databinding.FragmentFoundBinding
import com.architecture.light.ui.page.common.CommonActivity
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/14
 * Modify date: 2022/8/14
 * Version: 1
 */
class FoundFragment : AppFragment<CommonActivity>() {

    companion object {
        fun newInstance() = FoundFragment()
    }

    private val binding: FragmentFoundBinding by binding()

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
        binding.sbFindSwitch.setOnCheckedChangeListener { _, checked ->
            toast(checked.toString())
        }
    }

    override fun initData() {
        GlideApp.with(this)
            .load(R.drawable.example_bg)
            .transform(MultiTransformation(CenterCrop(), CircleCrop()))
            .into(binding.ivFindCircle)

        GlideApp.with(this)
            .load(R.drawable.example_bg)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(
                        resources.getDimension(com.android.architecture.R.dimen.dp_10).toInt()
                    )
                )
            )
            .into(binding.ivFindCorner)
    }

}