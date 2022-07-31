package com.architecture.sample.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.BaseFragment
import com.architecture.sample.R
import com.architecture.sample.data.model.db.entity.Note
import com.architecture.sample.databinding.FragmentListBinding
import com.architecture.sample.domain.event.Messages
import com.architecture.sample.domain.event.NoteEvent
import com.architecture.sample.domain.message.PageMessenger
import com.architecture.sample.domain.request.NoteRequester
import com.architecture.sample.ui.adapter.NoteAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class ListFragment : BaseFragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val state by viewModels<ListViewModel>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val adapter by lazy { NoteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun initView() {
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
                    it.notes?.apply {
                        this.forEach { note ->
                            Logger.d(TAG, note.toString())
                        }
                    }
                    state.list = it.notes!!.toMutableList()
                    adapter.setData(state.list)
                    binding.ivEmpty.visibility =
                        if (state.list.isEmpty()) View.VISIBLE else View.GONE
                }
                else -> {}
            }
        }
    }

    override fun input() {
        noteRequester.input(NoteEvent.GetNoteList())
        binding.fab.click {
            EditorFragment.start(nav(), Note())
        }
    }

    class ListViewModel : ViewModel() {
        var list = mutableListOf<Note>()
    }

}