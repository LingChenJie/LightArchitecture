package com.architecture.light.app

import android.os.Bundle
import android.view.ViewGroup
import com.android.architecture.ui.page.BaseActivity
import com.android.architecture.ui.page.BaseDialog
import com.android.architecture.ui.widget.layout.TitleView
import com.architecture.light.R
import com.architecture.light.ui.dialog.LoadingDialog
import com.gyf.immersionbar.ImmersionBar

abstract class AppActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return true
    }

    /**
     * 状态栏字体深色模式
     */
    open fun isStatusBarDarkFont(): Boolean {
        return true
    }

    private var mLoadingDialog: BaseDialog? = null

    fun showLoading(message: String = getString(R.string.common_loading)) {
        hideLoading()
        mLoadingDialog = LoadingDialog.Builder(this)
            .message(message)
            .setCancelable(false)
            .create()
        mLoadingDialog?.show()
    }

    fun hideLoading() {
        mLoadingDialog?.apply {
            if (isShowing) {
                dismiss()
            }
        }
        mLoadingDialog = null
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
        return obtainTitleBar(contentView)
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
                return view
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