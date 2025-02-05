package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.R
import com.architecture.light.data.local.db.entity.Note
import com.architecture.light.databinding.AdapterNoteListBinding

class NoteAdapter : BaseAdapter<Note, AdapterNoteListBinding>() {

    override fun getViewBinding(parent: ViewGroup): AdapterNoteListBinding {
        return AdapterNoteListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
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
        binding.tvTime.text = DateHelper.getDateFormatString(millis = item.modifyTime)
        binding.btnMark.setImageResource(if (item.isMarked) R.drawable.icon_star else R.drawable.icon_star_board)
        binding.tvTopped.visibility = if (item.isTopping) View.VISIBLE else View.GONE
        binding.btnLock.setImageResource(if (item.isLocked) R.drawable.icon_unlock else R.drawable.icon_lock)
        binding.layoutItem.click { mOnItemClickListener?.onItemClick(it.id, position, item) }
        binding.btnDelete.click {
            notifyItemRemoved(position)
            data.removeAt(position)
            notifyItemRangeRemoved(position, data.size - position)
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
        binding.btnMark.click {
            item.toggleType(Note.TYPE_MARKED)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, 1)
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
        binding.btnTopping.click {
            item.toggleType(Note.TYPE_TOPPING)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, 1)
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
        binding.btnLock.click {
            item.toggleType(Note.TYPE_LOCKED)
            notifyItemRemoved(position)
            data.removeAt(position)
            notifyItemRangeRemoved(position, data.size - position)
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}