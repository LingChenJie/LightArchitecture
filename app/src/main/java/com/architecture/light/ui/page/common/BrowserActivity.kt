package com.architecture.light.ui.page.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import com.android.architecture.extension.argument
import com.android.architecture.extension.binding
import com.android.architecture.helper.DelayHelper
import com.architecture.light.action.StatusAction
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityBrowserBinding
import com.architecture.light.ui.view.BrowserView
import com.architecture.light.ui.view.StatusLayout
import com.hjq.bar.OnTitleBarListener
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/11
 * Modify date: 2022/11/11
 * Version: 1
 */
class BrowserActivity : AppActivity(), StatusAction, OnRefreshListener {

    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("url", url)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private val binding: ActivityBrowserBinding by binding()
    private val url: String by argument()

    override fun isStatusBarEnabled(): Boolean {
        return !super.isStatusBarEnabled()
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(view: View?) {
                finish()
            }

            override fun onTitleClick(view: View?) {
            }

            override fun onRightClick(view: View?) {
            }
        })
        binding.browserView.setLifecycleOwner(this)
        binding.smartRefreshLayout.setOnRefreshListener(this)
    }

    override fun initData() {
        showLoadingStatus()
        binding.browserView.setBrowserViewClient(AppBrowserViewClient())
        binding.browserView.setBrowserChromeClient(AppBrowserChromeClient(binding.browserView))
        Log.i(TAG, "url:$url")
        binding.browserView.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.browserView.canGoBack()) {
            binding.browserView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getStatusLayout(): StatusLayout {
        return binding.statusLayout
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        reload()
    }

    private fun reload() {
        binding.browserView.reload()
    }

    inner class AppBrowserViewClient : BrowserView.BrowserViewClient() {
        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            // 这里为什么要用延迟呢？因为加载出错之后会先调用 onReceivedError 再调用 onPageFinished
            DelayHelper.sendDelayTask(0) {
                showErrorStatus { reload() }
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            binding.progress.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.progress.visibility = View.GONE
            binding.smartRefreshLayout.finishRefresh()
            showCompleteStatus()
        }
    }

    inner class AppBrowserChromeClient(view: BrowserView) : BrowserView.BrowserChromeClient(view) {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            if (title == null) return
            binding.titleBar.title = title
        }

        override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
            if (icon == null) return
            binding.titleBar.rightIcon = BitmapDrawable(resources, icon)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            binding.progress.progress = newProgress
        }
    }

}