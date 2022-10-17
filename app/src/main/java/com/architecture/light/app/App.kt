package com.architecture.light.app

import android.content.Context
import com.android.architecture.app.BaseApplication
import com.android.architecture.constant.ErrorCode
import com.architecture.light.constant.AppErrorCode
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
        ErrorCode.add(AppErrorCode.errorCodeMap)
        init()
    }

    private fun init() {
        // 设置全局初始化器
        SmartRefreshLayout.setDefaultRefreshInitializer { context: Context, layout: RefreshLayout ->
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
    }

}