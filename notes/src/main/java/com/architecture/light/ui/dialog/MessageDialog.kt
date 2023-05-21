package com.architecture.light.ui.dialog

import android.content.Context
import android.widget.TextView
import com.android.architecture.extension.empty
import com.android.architecture.ui.page.BaseDialog
import com.architecture.light.R

class MessageDialog {

    @Suppress("UNCHECKED_CAST")
    class Builder(context: Context) : CommonDialog.Builder<Builder>(context) {

        private val tvMessage: TextView

        init {
            setCustomView(R.layout.dialog_message)
            tvMessage = findViewById(R.id.tv_message_message)
        }

        fun message(text: String): Builder {
            tvMessage.text = text
            return this
        }

        override fun create(): BaseDialog {
            if (tvMessage.text.toString().empty) {
                throw IllegalArgumentException("Dialog message not null")
            }
            return super.create()
        }

    }

}