package com.architecture.light.ui.page.mvi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.android.architecture.extension.*
import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.BaseState
import com.architecture.light.R
import com.architecture.light.app.AppFragment
import com.architecture.light.data.local.db.entity.Note
import com.architecture.light.databinding.FragmentEditorBinding
import com.architecture.light.domain.event.MviMessages
import com.architecture.light.domain.event.NoteEvent
import com.architecture.light.domain.message.NoteRequester
import com.architecture.light.domain.message.PageMessenger
import com.architecture.light.ui.page.mvi.NotesActivity
import com.gyf.immersionbar.ImmersionBar

class EditorFragment : AppFragment<NotesActivity>() {

    companion object {
        private const val NOTE = "NOTE"
        private const val TYPE = "TYPE"
        fun start(controller: NavController, note: Note?) {
            val bundle = Bundle()
            bundle.putParcelable(NOTE, note)
            bundle.putInt(TYPE, Note.TYPE_NORMAL)
            controller.navigate(R.id.action_listFragment_to_editorFragment, bundle)
        }
        fun startByLock(controller: NavController, note: Note?) {
            val bundle = Bundle()
            bundle.putParcelable(NOTE, note)
            bundle.putInt(TYPE, Note.TYPE_LOCKED)
            controller.navigate(R.id.action_lockListFragment_to_editorFragment, bundle)
        }
    }

    private val binding: FragmentEditorBinding by binding()
    private val state by viewModels<State>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private var type = Note.TYPE_NORMAL

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        if (arguments != null) {
            type = requireArguments().getInt(TYPE)
            state.originNote = requireArguments().getParcelable(NOTE)!!
            state.originNote.apply {
                state.title = this.title
                state.content = this.content
                if (this.nId == 0L) {
                    binding.etTitle.requestFocus()
                    binding.etTitle.toggleSoftInput()
                } else {
                    binding.etTitle.setText(this.title)
                    binding.etContent.setText(this.content)
                    binding.tvTitle.text = getString(R.string.last_time_modify)
                    binding.tvTime.text = DateHelper.getDateFormatString(millis = this.modifyTime)
                }
            }
        }
    }

    override fun input() {
        super.input()
        binding.ivBack.click { save() }
    }

    override fun output() {
        super.output()
        noteRequester.output(this) { noteEvent ->
            if (noteEvent is NoteEvent.AddItem) {
                messenger.input(MviMessages.RefreshNoteList)
                toast(getString(R.string.saved))
                navController.navigateUp()
            }
        }
        messenger.output(this) {
            Logger.e(TAG, "it:$it")
        }
    }

    override fun onBackPressed(): Boolean {
        save()
        return true
    }

    private fun save() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.empty || content.empty) {
            navController.navigateUp()
            return
        }
        val time = System.currentTimeMillis()
        if (state.originNote.nId == 0L) {
            state.tempNote = Note(0, title, content, time, time, type)
        } else {
            state.originNote.apply {
                state.tempNote = Note(
                    this.nId,
                    title,
                    content,
                    this.creteTime,
                    time,
                    this.type
                )
            }
        }
        noteRequester.input(NoteEvent.AddItem.setNote(state.tempNote))
    }

    class State : BaseState() {
        var originNote: Note = Note()
        var tempNote: Note = Note()
        var title: String = ""
        var content: String = ""
    }

}