package com.architecture.light.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.databinding.ItemCommonNavigationBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/13
 * Modify date: 2022/8/13
 * Version: 1
 */
class NavigationAdapter :
    BaseAdapter<NavigationAdapter.MenuItem, ItemCommonNavigationBinding>() {

    class MenuItem(val text: String, val drawable: Drawable)

    // 当前选中条目位置
    private var mSelectPosition = 0
    private var mNavigationListener: ((position: Int) -> Unit)? = null

    override fun getViewBinding(parent: ViewGroup): ItemCommonNavigationBinding {
        return ItemCommonNavigationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<ItemCommonNavigationBinding>,
        item: MenuItem,
        position: Int
    ) {
        val binding = holder.binding
        binding.ivNavigationIcon.setImageDrawable(item.drawable)
        binding.tvNavigationTitle.text = item.text
        binding.ivNavigationIcon.isSelected = mSelectPosition == position
        binding.tvNavigationTitle.isSelected = mSelectPosition == position
        binding.root.click {
            if (mSelectPosition == position) {
                return@click
            }
            mSelectPosition = position
            mNavigationListener?.invoke(position)
            notifyDataSetChanged()
        }
    }

    fun setSelectedPosition(position: Int) {
        mSelectPosition = position
        notifyDataSetChanged()
    }

    fun setOnNavigationListener(listener: (position: Int) -> Unit) {
        mNavigationListener = listener
    }

}