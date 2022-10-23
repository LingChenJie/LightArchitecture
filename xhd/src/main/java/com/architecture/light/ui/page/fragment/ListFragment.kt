package com.architecture.light.ui.page.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.BaseState
import com.architecture.light.R
import com.architecture.light.app.AppFragment
import com.architecture.light.data.model.db.entity.Note
import com.architecture.light.databinding.FragmentListBinding
import com.architecture.light.domain.event.ComplexEvent
import com.architecture.light.domain.event.Messages
import com.architecture.light.domain.event.NoteEvent
import com.architecture.light.domain.message.PageMessenger
import com.architecture.light.domain.request.ComplexRequester
import com.architecture.light.domain.request.NoteRequester
import com.architecture.light.ui.adapter.NoteAdapter
import com.architecture.light.ui.page.activity.MviActivity
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
    private val state by viewModels<State>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val complexRequester by activityViewModels<ComplexRequester>()
    private val adapter by lazy { NoteAdapter() }

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        binding.recyclerView.adapter = adapter
    }

    override fun input() {
        noteRequester.input(NoteEvent.GetNoteList())
        binding.fab.click {
            EditorFragment.start(navController, Note())
        }
        adapter.setItemClickListener { viewId, position, item ->
            when (viewId) {
                R.id.layout_item -> {
                    EditorFragment.start(navController, item)
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

    override fun output() {
        messenger.output(this) {
            Logger.e(TAG, "it:$it")
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
        complexRequester.output(this) {
            when (it) {
                is ComplexEvent.ResultTest1 -> {
                    Logger.d(TAG, "--1")
                }
                is ComplexEvent.ResultTest2 -> {
                    Logger.d(TAG, "--2")
                }
                is ComplexEvent.ResultTest3 -> {
                    Logger.d(TAG, "--3")
                }
                is ComplexEvent.ResultTest4 -> {
                    Logger.d(TAG, "--4 " + it.count)
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        messenger.input(Messages.FinishActivity)
        return true
    }

    class State : BaseState() {
        var list = mutableListOf<Note>()
    }

}