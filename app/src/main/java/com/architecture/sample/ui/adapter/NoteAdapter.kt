package com.architecture.sample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.sample.R
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

    override fun getViewBinding(viewGroup: ViewGroup): AdapterNoteListBinding {
        return AdapterNoteListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterNoteListBinding>,
        item: Note,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTitle.text = item.title
        binding.layoutItem.clipToOutline = true
        binding.tvTime.text = DateHelper.getDateFormatString(item.modifyTime)
        binding.btnMark.setImageResource(if (item.isMarked) R.drawable.icon_star else R.drawable.icon_star_board)
        binding.tvTopped.visibility = if (item.isTopping) View.VISIBLE else View.GONE
        binding.layoutItem.click { listener?.onItemClick(it.id, position, item) }
        binding.btnDelete.click {
            notifyItemRemoved(position)
            data.removeAt(position)
            notifyItemRangeRemoved(position, data.size - position)
            listener?.onItemClick(it.id, position, item)
        }
        binding.btnMark.click {
            item.toggleType(Note.TYPE_MARKED)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, 1)
            listener?.onItemClick(it.id, position, item)
        }
        binding.btnTopping.click {
            item.toggleType(Note.TYPE_TOPPING)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, 1)
            listener?.onItemClick(it.id, position, item)
        }
    }
}