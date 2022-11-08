package com.architecture.light.ui.page.common.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.android.architecture.helper.DelayHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.app.AppActivity
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentStatusBinding
import com.architecture.light.databinding.ItemStatusBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Created by SuQi on 2022/8/21.
 * Describe:
 */
class StatusFragment : AppFragment<AppActivity>(), OnRefreshListener, OnRefreshLoadMoreListener {

    companion object {
        fun newInstance() = StatusFragment()
    }

    private val binding: FragmentStatusBinding by binding()
    private val adapter = StatusAdapter()

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup): View {
        return binding.root
    }

    override fun initView() {
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this)
        binding.recyclerView.adapter = adapter
        adapter.data = analogData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        DelayHelper.sendDelayTask(1000) {
            adapter.data = analogData()
            binding.smartRefreshLayout.finishRefresh()
            binding.smartRefreshLayout.setNoMoreData(adapter.data.size >= 100)
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        DelayHelper.sendDelayTask(1000) {
            adapter.addData(analogData())
            binding.smartRefreshLayout.finishRefresh()
        }
    }

    private fun analogData(): List<String> {
        val data: MutableList<String> = ArrayList()
        for (i in adapter.data.size until adapter.data.size + 20) {
            data.add("我是第" + i + "条目")
        }
        return data
    }


    class StatusAdapter : BaseAdapter<String, ItemStatusBinding>() {
        override fun getViewBinding(parent: ViewGroup): ItemStatusBinding {
            return ItemStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }

        override fun bindViewHolder(
            holder: ViewHolder<ItemStatusBinding>,
            item: String,
            position: Int
        ) {
            val binding = holder.binding
            binding.tvStatusText.text = item
        }
    }

}