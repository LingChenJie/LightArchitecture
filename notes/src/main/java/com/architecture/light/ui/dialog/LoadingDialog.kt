package com.architecture.light.ui.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.android.architecture.extension.empty
import com.android.architecture.ui.page.BaseDialog
import com.architecture.light.R

class LoadingDialog {

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context) {

        private val tvMessage: TextView

        init {
            setContentView(R.layout.dialog_loading)
            setAnimStyle(BaseDialog.ANIM_TOAST)
            setBackgroundDimEnabled(false)
            setCancelable(false)
            tvMessage = findViewById(R.id.tv_wait_message)
        }

        fun message(text: String): Builder {
            tvMessage.text = text
            tvMessage.visibility = if (text.empty) View.GONE else View.VISIBLE
            return this
        }

    }
}