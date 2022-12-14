package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.extension.getColor
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.R
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.databinding.AdapterProjectListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ProjectChooseAdapter :
    BaseAdapter<LoginResponse.Data.Project, AdapterProjectListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterProjectListBinding {
        return AdapterProjectListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterProjectListBinding>,
        item: LoginResponse.Data.Project,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvProjectName.text = item.projName
        binding.ivChecked.isChecked = item.isChecked
        binding.root.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}