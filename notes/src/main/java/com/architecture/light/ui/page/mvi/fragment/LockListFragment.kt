package com.architecture.light.ui.page.mvi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.BaseState
import com.architecture.light.R
import com.architecture.light.app.AppFragment
import com.architecture.light.data.local.db.entity.Note
import com.architecture.light.databinding.FragmentListBinding
import com.architecture.light.databinding.FragmentLockListBinding
import com.architecture.light.domain.event.ComplexEvent
import com.architecture.light.domain.event.MviMessages
import com.architecture.light.domain.event.NoteEvent
import com.architecture.light.domain.message.ComplexRequester
import com.architecture.light.domain.message.NoteRequester
import com.architecture.light.domain.message.PageMessenger
import com.architecture.light.ui.adapter.NoteAdapter
import com.architecture.light.ui.page.mvi.NotesActivity
import com.gyf.immersionbar.ImmersionBar

class LockListFragment : AppFragment<NotesActivity>() {

    companion object {
        fun start(controller: NavController) {
            val bundle = Bundle()
            controller.navigate(R.id.action_listFragment_to_lockListFragment, bundle)
        }
    }

    private val binding: FragmentLockListBinding by binding()
    private val state by viewModels<State>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val adapter by lazy { NoteAdapter() }

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        binding.recyclerView.adapter = adapter
    }

    override fun input() {
        noteRequester.input(NoteEvent.GetLockNoteList())
        binding.fab.click {
            EditorFragment.startByLock(navController, Note())
        }
        adapter.setItemClickListener { viewId, position, item ->
            when (viewId) {
                R.id.layout_item -> {
                    EditorFragment.startByLock(navController, item)
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
                R.id.btn_lock -> {
                    noteRequester.input(NoteEvent.LockItem.setNote(item.copy()))
                }
            }
        }
    }

    override fun output() {
        messenger.output(this) {
            Logger.e(TAG, "it:$it")
            if (it is MviMessages.RefreshNoteList) {
                noteRequester.input(NoteEvent.GetLockNoteList())
            }
        }
        noteRequester.output(this) {
            when (it) {
                is NoteEvent.GetLockNoteList -> {
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

    override fun onBackPressed(): Boolean {
        navController.navigateUp()
        messenger.input(MviMessages.RefreshNoteList)
        return true
    }

    class State : BaseState() {
        var list = mutableListOf<Note>()
    }

}