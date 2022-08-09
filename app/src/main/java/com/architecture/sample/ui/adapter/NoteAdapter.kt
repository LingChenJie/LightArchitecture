package com.architecture.sample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.sample.data.model.db.entity.Note
import com.architecture.sample.databinding.AdapterNoteListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class NoteAdapter : BaseAdapter<Note, AdapterNoteListBinding>() {

    override fun onBindingView(viewGroup: ViewGroup): AdapterNoteListBinding {
        return AdapterNoteListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun onBindingData(
        holder: ViewHolder<AdapterNoteListBinding>,
        item: Note,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTitle.text = item.title
        binding.layoutItem.clipToOutline = true
        binding.tvTime.text = DateHelper.getDateFormatString(item.modifyTime)
        binding.btnDelete.click {
            notifyItemRemoved(position)
            data.removeAt(position)
            notifyItemRangeRemoved(position, data.size - position)
            if (listener != null) {
                listener.onItemClick(it.id, position, item)
            }
        }
    }
}