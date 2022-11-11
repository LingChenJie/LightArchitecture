package com.architecture.light.ui.page.common.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.android.architecture.extension.argument
import com.android.architecture.extension.binding
import com.android.architecture.helper.DelayHelper
import com.architecture.light.action.StatusAction
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentBrowserBinding
import com.architecture.light.ui.page.common.BrowserActivity
import com.architecture.light.ui.page.common.CommonActivity
import com.architecture.light.ui.view.BrowserView
import com.architecture.light.ui.view.StatusLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * Created by SuQi on 2022/8/21.
 * Describe:
 */
class BrowserFragment : AppFragment<CommonActivity>(), StatusAction, OnRefreshListener {

    companion object {
        fun newInstance(url: String): BrowserFragment {
            val fragment = BrowserFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentBrowserBinding by binding()
    private val url: String by argument()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup): View {
        return binding.root
    }

    override fun initView() {
        binding.smartRefreshLayout.setOnRefreshListener(this)
        binding.browserView.setLifecycleOwner(this)
    }

    override fun initData() {
        binding.browserView.setBrowserViewClient(AppBrowserViewClient())
        binding.browserView.setBrowserChromeClient(BrowserView.BrowserChromeClient(binding.browserView))
        binding.browserView.loadUrl(url)
        showLoadingStatus()
    }

    override fun getStatusLayout(): StatusLayout {
        return binding.root
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

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.smartRefreshLayout.finishRefresh()
            showCompleteStatus()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
            val scheme = Uri.parse(url).scheme ?: return true
            when (scheme.lowercase()) {
                "http", "https" -> {
                    BrowserActivity.start(mActivity, url)
                }
                else -> {
                }
            }
            return true
        }
    }

}