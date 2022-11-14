package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.extension.toast
import com.architecture.light.app.AppFragment
import com.architecture.light.config.glide.GlideApp
import com.architecture.light.databinding.FragmentMessageBinding
import com.architecture.light.helper.PermissionsHelper
import com.architecture.light.ui.page.common.CommonActivity
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

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
        binding.btnMessageImage1.click {
            binding.ivMessageImage.visibility = View.VISIBLE
            GlideApp.with(this)
                .load("https://www.baidu.com/img/bd_logo.png")
                .into(binding.ivMessageImage)
        }
        binding.btnMessageImage2.click {
            binding.ivMessageImage.visibility = View.VISIBLE
            GlideApp.with(this)
                .load("https://www.baidu.com/img/bd_logo.png")
                .circleCrop()
                .into(binding.ivMessageImage)
        }
        binding.btnMessageImage3.click {
            binding.ivMessageImage.visibility = View.VISIBLE
            GlideApp.with(this)
                .load("https://www.baidu.com/img/bd_logo.png")
                .transform(
                    RoundedCorners(
                        resources.getDimension(com.android.architecture.R.dimen.dp_20).toInt()
                    )
                )
                .into(binding.ivMessageImage)
        }
        binding.btnMessageToast.click {
            toast("我是吐司")
        }
        binding.btnMessagePermission.click {
            PermissionsHelper.requirePermissions(Permission.CAMERA) {
                toast("获取摄像头权限成功")
            }
        }
        binding.btnMessageSetting.click {
            XXPermissions.startPermissionActivity(this)
        }
        binding.btnMessageBlack.click {
            getStatusBarConfig()
                .statusBarDarkFont(true)
                .init()
        }
        binding.btnMessageWhite.click {
            getStatusBarConfig()
                .statusBarDarkFont(false)
                .init()
        }
        binding.btnMessageTab.click {

        }
    }

}