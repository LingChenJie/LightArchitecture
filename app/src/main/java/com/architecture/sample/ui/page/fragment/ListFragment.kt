package com.architecture.sample.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.architecture.sample.R
import com.architecture.sample.app.AppFragment
import com.architecture.sample.data.model.db.entity.Note
import com.architecture.sample.databinding.FragmentListBinding
import com.architecture.sample.domain.event.Messages
import com.architecture.sample.domain.event.NoteEvent
import com.architecture.sample.domain.message.PageMessenger
import com.architecture.sample.domain.request.NoteRequester
import com.architecture.sample.ui.adapter.NoteAdapter
import com.architecture.sample.ui.page.activity.MviActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class ListFragment : AppFragment<MviActivity>() {

    private lateinit var binding: FragmentListBinding
    private val state by viewModels<ListViewModel>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val adapter by lazy { NoteAdapter() }

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        binding.recyclerView.adapter = adapter
    }

    override fun output() {
        messenger.output(this) {
            if (it is Messages.RefreshNoteList) {
                noteRequester.input(NoteEvent.GetNoteList())
            }
        }
        noteRequester.output(this) {
            when (it) {
                is NoteEvent.GetNoteList -> {
                    it.notes?.onEach { note ->
                        Logger.d(TAG, note.toString())
                    }
                    state.list = it.notes!!.toMutableList()
                    adapter.setData(state.list)
                    binding.ivEmpty.visibility =
                        if (state.list.isEmpty()) View.VISIBLE else View.GONE
                }
                is NoteEvent.RemoveItem -> {}
                is NoteEvent.MarkItem -> {}
                is NoteEvent.ToppingItem -> {}
                else -> {}
            }
        }
    }

    override fun input() {
        noteRequester.input(NoteEvent.GetNoteList())
        binding.fab.click {
            EditorFragment.start(nav(), Note())
        }
        adapter.setItemClickListener { viewId, position, item ->
            when (viewId) {
                R.id.layout_item -> {
                    EditorFragment.start(nav(), item)
                }
                R.id.btn_delete -> {
                    noteRequester.input(NoteEvent.RemoveItem.setNote(item.copy()))
                }
                R.id.btn_mark -> {
                    noteRequester.input(NoteEvent.MarkItem.setNote(item.copy()))
                }
                R.id.btn_topping -> {
                    noteRequester.input(NoteEvent.ToppingItem.setNote(item.copy()))
                }
            }
        }
    }

    override fun isInterceptBackEvent(): Boolean {
        return true
    }

    override fun onBackPressed() {
        messenger.input(Messages.FinishActivity)
    }

    class ListViewModel : ViewModel() {
        var list = mutableListOf<Note>()
    }

}