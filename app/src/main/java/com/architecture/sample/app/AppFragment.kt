package com.architecture.sample.app

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.ui.page.BaseFragment
import com.android.architecture.ui.widget.layout.TitleView
import com.gyf.immersionbar.ImmersionBar

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/13
 * Modify date: 2022/8/13
 * Version: 1
 */
abstract class AppFragment<A : BaseActivity> : BaseFragment<A>() {

    override fun onResume() {
        super.onResume()
        if (isStatusBarEnabled()) {
            // 重新初始化状态栏
            getStatusBarConfig().init()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isStatusBarEnabled()) {
            getStatusBarConfig().init()
            getTitleView()?.let {
                ImmersionBar.setTitleBar(this, it)
            }
        }
    }


    /**
     * 是否使用沉浸式状态栏
     */
    open fun isStatusBarEnabled(): Boolean {
        return false
    }

    /**
     * 状态栏字体深色模式
     */
    open fun isStatusBarDarkFont(): Boolean {
        return true
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    private fun getStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this)
            .statusBarDarkFont(isStatusBarDarkFont())
            .autoDarkModeEnable(true, 0.2f);
    }

    /**
     * 获取标题栏
     */
    private fun getTitleView(): TitleView? {
        return obtainTitleBar(rootView as ViewGroup)
    }

    /**
     * 递归获取 ViewGroup 中的 TitleBar 对象
     */
    private fun obtainTitleBar(group: ViewGroup?): TitleView? {
        if (group == null) {
            return null
        }
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is TitleView) {
                return view as TitleView
            }
            if (view is ViewGroup) {
                val titleBar: TitleView? = obtainTitleBar(view)
                if (titleBar != null) {
                    return titleBar
                }
            }
        }
        return null
    }
}