package com.architecture.light.app

import android.content.Context
import androidx.core.content.ContextCompat
import com.android.architecture.app.BaseApplication
import com.android.architecture.constant.ErrorCode
import com.android.architecture.helper.Logger
import com.android.architecture.utils.DeviceInfoUtils
import com.architecture.light.R
import com.architecture.light.config.ToastStyle
import com.architecture.light.config.glide.GlideApp
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.constant.Constant.TAG
import com.architecture.light.ui.view.SmartBallPulseFooter
import com.hjq.toast.ToastUtils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.e(Constant.TAG, "onCreate()")
        ErrorCode.add(AppErrorCode.errorCodeMap)
        init()
    }

    private fun init() {
        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, _: RefreshLayout? ->
            MaterialHeader(this).setColorSchemeColors(
                ContextCompat.getColor(this, R.color.theme_color_90)
            )
        }
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { _: Context?, _: RefreshLayout? ->
            SmartBallPulseFooter(this)
        }
        // 设置全局初始化器
        SmartRefreshLayout.setDefaultRefreshInitializer { _: Context, layout: RefreshLayout ->
            // 刷新头部是否跟随内容偏移
            layout.setEnableHeaderTranslationContent(true)
                // 刷新尾部是否跟随内容偏移
                .setEnableFooterTranslationContent(true)
                // 加载更多是否跟随内容偏移
                .setEnableFooterFollowWhenNoMoreData(true)
                // 内容不满一页时是否可以上拉加载更多
                .setEnableLoadMoreWhenContentNotFull(false)
                // 仿苹果越界效果开关
                .setEnableOverScrollDrag(false)
        }

        // 初始化吐司
        ToastUtils.init(this, ToastStyle())
        val deviceInfo = DeviceInfoUtils.getDeviceInfo()
        Logger.e(TAG, "deviceInfo: $deviceInfo")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).onTrimMemory(level)
    }

}