package com.architecture.sample.ui.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.extension.toast
import com.android.architecture.extension.toggleSoftInput
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.page.StateHolder
import com.architecture.sample.R
import com.architecture.sample.app.AppFragment
import com.architecture.sample.data.model.db.entity.Note
import com.architecture.sample.databinding.FragmentEditorBinding
import com.architecture.sample.domain.event.Messages
import com.architecture.sample.domain.event.NoteEvent
import com.architecture.sample.domain.message.PageMessenger
import com.architecture.sample.domain.request.NoteRequester
import com.architecture.sample.ui.page.activity.MviActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class EditorFragment : AppFragment<MviActivity>() {

    companion object {
        private const val NOTE = "NOTE"
        fun start(controller: NavController, note: Note?) {
            val bundle = Bundle()
            bundle.putParcelable(NOTE, note)
            controller.navigate(R.id.action_listFragment_to_editorFragment, bundle)
        }
    }

    private lateinit var binding: FragmentEditorBinding
    private val states by viewModels<EditorState>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        if (arguments != null) {
            states.originNote = requireArguments().getParcelable(NOTE)!!
            states.originNote.apply {
                states.title = this.title
                states.content = this.content
                if (this.nId == 0L) {
                    binding.etTitle.requestFocus()
                    binding.etTitle.toggleSoftInput()
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

    class EditorState : StateHolder() {
        var originNote: Note = Note()
        var tempNote: Note = Note()
        var title: String = ""
        var content: String = ""
    }

}