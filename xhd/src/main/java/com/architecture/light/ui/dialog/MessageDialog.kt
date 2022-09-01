package com.architecture.light.ui.dialog

import android.content.Context
import android.widget.TextView
import com.android.architecture.extension.empty
import com.android.architecture.ui.page.BaseDialog
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MessageDialog {

    @Suppress("UNCHECKED_CAST")
    class Builder<B : Builder<B>>(context: Context) : CommonDialog.Builder<B>(context) {

        private val tvMessage: TextView

        init {
            setCustomView(R.layout.dialog_message)
            tvMessage = findViewById(R.id.tv_message_message)
        }

        fun message(text: String): B {
            tvMessage.text = text
            return this as B
        }

        override fun create(): BaseDialog {
            if (tvMessage.text.toString().empty) {
                throw IllegalArgumentException("Dialog message not null")
            }
            return super.create()
        }

    }

}