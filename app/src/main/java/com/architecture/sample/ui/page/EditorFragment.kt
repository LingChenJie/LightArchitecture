package com.architecture.sample.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.extension.toast
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.page.BaseFragment
import com.architecture.sample.R
import com.architecture.sample.data.model.db.entity.Note
import com.architecture.sample.databinding.FragmentEditorBinding
import com.architecture.sample.domain.event.Messages
import com.architecture.sample.domain.event.NoteEvent
import com.architecture.sample.domain.message.PageMessenger
import com.architecture.sample.domain.request.NoteRequester

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class EditorFragment : BaseFragment<MainActivity>() {

    companion object {
        private const val NOTE = "NOTE"
        fun start(controller: NavController, note: Note?) {
            val bundle = Bundle()
            bundle.putParcelable(NOTE, note)
            controller.navigate(R.id.action_listFragment_to_editorFragment)
        }
    }

    private lateinit var binding: FragmentEditorBinding
    private val states by viewModels<EditorViewModel>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun initView() {
        if (arguments != null) {
            states.originNote = requireArguments().getParcelable(NOTE)!!
            states.originNote?.apply {
                states.title = this.title
                states.content = this.content
                if (this.nId == 0L) {
                    binding.etTitle.requestFocus()
                } else {
                    binding.etTitle.setText(this.title)
                    binding.etContent.setText(this.content)
                    binding.tvTitle.text = getString(R.string.last_time_modify)
                    binding.tvTime.text = DateHelper.getDateFormatString(this.modifyTime)
                }
            }

        }
    }

    override fun output() {
        super.output()
        noteRequester.output(this) { noteEvent ->
            if (noteEvent is NoteEvent.AddItem) {
                messenger.input(Messages.RefreshNoteList)
                toast(getString(R.string.saved))
                nav().navigateUp()
            }
        }
    }

    override fun input() {
        super.input()
        binding.ivBack.click { save() }
    }

    override fun onBackPressed(): Boolean {
        save()
        return true
    }

    private fun save() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.empty || content.empty) {
            nav().navigateUp()
            return
        }
        val time = System.currentTimeMillis()
        if (states.originNote.nId == 0L) {
            states.tempNote = Note(0, title, content, time, time, 0)
        } else {
            states.originNote.apply {
                states.tempNote = Note(
                    this.nId,
                    title,
                    content,
                    this.creteTime,
                    time,
                    this.type
                )
            }
        }
        noteRequester.input(NoteEvent.AddItem.setNote(states.tempNote))
    }

    class EditorViewModel : ViewModel() {
        var originNote: Note = Note()
        var tempNote: Note = Note()
        var title: String = ""
        var content: String = ""
    }

}