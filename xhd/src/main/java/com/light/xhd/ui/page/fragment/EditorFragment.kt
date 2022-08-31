package com.light.xhd.ui.page.fragment

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
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.StateHolder
import com.architecture.sample.app.AppFragment
import com.light.xhd.data.model.db.entity.Note
import com.light.xhd.domain.event.Messages
import com.light.xhd.domain.event.NoteEvent
import com.light.xhd.domain.message.PageMessenger
import com.light.xhd.domain.request.NoteRequester
import com.light.xhd.ui.page.activity.MviActivity
import com.gyf.immersionbar.ImmersionBar
import com.light.xhd.R
import com.light.xhd.databinding.FragmentEditorBinding

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
    private val state by viewModels<State>()
    private val noteRequester by viewModels<NoteRequester>()
    private val messenger by activityViewModels<PageMessenger>()

    override fun isStatusBarEnabled(): Boolean {
        return true
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, binding.titleView)
        if (arguments != null) {
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
                    binding.tvTime.text = DateHelper.getDateFormatString(this.modifyTime)
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
                messenger.input(Messages.RefreshNoteList)
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
            state.tempNote = Note(0, title, content, time, time, 0)
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

    class State : StateHolder() {
        var originNote: Note = Note()
        var tempNote: Note = Note()
        var title: String = ""
        var content: String = ""
    }

}