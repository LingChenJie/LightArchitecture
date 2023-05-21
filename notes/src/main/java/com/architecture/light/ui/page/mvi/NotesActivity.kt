package com.architecture.light.ui.page.mvi

import androidx.activity.viewModels
import com.android.architecture.extension.binding
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNotesBinding
import com.architecture.light.domain.event.MviMessages
import com.architecture.light.domain.message.PageMessenger

class NotesActivity : AppActivity() {

    private val binding: ActivityNotesBinding by binding()
    private val messenger by viewModels<PageMessenger>()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
    }

    override fun input() {
    }

    override fun output() {
        messenger.output(this) {
            if (it is MviMessages.FinishActivity) {
                finish()
            }
        }
    }

}