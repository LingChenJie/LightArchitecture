package com.architecture.sample.ui.dialog

import android.content.Context
import android.widget.TextView
import androidx.annotation.StringRes
import com.android.architecture.extension.empty
import com.android.architecture.extension.getString
import com.android.architecture.ui.page.BaseDialog
import com.architecture.sample.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MessageDialog {

    class Builder<B : Builder<B>>(context: Context) : CommonDialog.Builder<B>(context) {

        private val tvMessage: TextView

        init {
            setCustomView(R.layout.dialog_message)
            tvMessage = findViewById(R.id.tv_message_message)
        }

        fun message(@StringRes id: Int): B {
            return message(getString(id))
        }

        fun message(text: CharSequence): B {
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