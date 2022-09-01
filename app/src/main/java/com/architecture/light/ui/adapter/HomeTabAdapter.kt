package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.architecture.extension.click
import com.android.architecture.extension.getContext
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.databinding.ItemHomeTabBinding

/**
 * Created by SuQi on 2022/8/19.
 * Describe:
 */
class HomeTabAdapter(private val fixedWidth: Boolean = true) :
    BaseAdapter<String, ItemHomeTabBinding>() {

    private var onTabListener: OnTabListener? = null
    private var selectedPosition = 0
        set(value) {
            if (field == value) {
                return
            }
            notifyItemChanged(field)
            field = value
            notifyItemChanged(value)
        }

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                refreshLayoutManager()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                refreshLayoutManager()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                refreshLayoutManager()
                if (selectedPosition > positionStart - itemCount) {
                    selectedPosition = positionStart - itemCount
                }
            }
        })
    }

    override fun getViewBinding(viewGroup: ViewGroup): ItemHomeTabBinding {
        val binding =
            ItemHomeTabBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        binding.root.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        return binding
    }

    override fun bindViewHolder(
        holder: ViewHolder<ItemHomeTabBinding>,
        item: String,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTitle.text = item
        binding.tvTitle.isSelected = selectedPosition == position
        binding.vLine.visibility =
            if (selectedPosition == position) View.VISIBLE else View.INVISIBLE
        binding.root.click {
            if (selectedPosition == position) {
                return@click
            }
            onTabListener?.onTabSelected(recyclerView, position)
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    override fun generateDefaultLayoutManager(): RecyclerView.LayoutManager {
        if (fixedWidth) {
            var count = getCount()
            if (count < 1) {
                count = 1
            }
            return GridLayoutManager(getContext(), count, RecyclerView.VERTICAL, false)
        } else {
            return LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    fun setOnTabListener(listener: OnTabListener) {
        onTabListener = listener
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // 禁用 RecyclerView 条目动画
        recyclerView.itemAnimator = null
    }

    private fun refreshLayoutManager() {
        if (!fixedWidth) {
            return
        }
        if (recyclerView == null) {
            return
        }
        recyclerView.layoutManager = generateDefaultLayoutManager()
    }

    private fun getCount(): Int {
        return data.size
    }

    interface OnTabListener {
        fun onTabSelected(recyclerView: RecyclerView, position: Int)
    }

}