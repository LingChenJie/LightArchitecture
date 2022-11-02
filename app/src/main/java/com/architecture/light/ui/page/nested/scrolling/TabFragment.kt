package com.architecture.light.ui.page.nested.scrolling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.architecture.extension.argument
import com.android.architecture.extension.binding
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.app.AppActivity
import com.architecture.light.app.AppFragment
import com.architecture.light.databinding.FragmentTabBinding
import com.architecture.light.databinding.ItemSingleTextBinding

/**
 * Created by SuQi on 2022/11/2.
 * Describe:
 */
class TabFragment : AppFragment<AppActivity>() {

    companion object {
        fun newInstance(text: String): TabFragment {
            val fragment = TabFragment()
            fragment.apply {
                val bundle = Bundle()
                bundle.putString("text", text)
                arguments = bundle
            }
            return fragment
        }
    }

    private val binding: FragmentTabBinding by binding()
    private val text: String by argument()

    override fun getRootView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        val adapter = SimpleStringAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        adapter.data = initStrings()
    }

    private fun initStrings(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..99) {
            list.add(text)
        }
        return list
    }

    class SimpleStringAdapter : BaseAdapter<String, ItemSingleTextBinding>() {
        override fun getViewBinding(viewGroup: ViewGroup): ItemSingleTextBinding {
            return ItemSingleTextBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

        override fun bindViewHolder(
            holder: ViewHolder<ItemSingleTextBinding>,
            item: String,
            position: Int
        ) {
            holder.binding.tvText.text = item
        }

    }
}