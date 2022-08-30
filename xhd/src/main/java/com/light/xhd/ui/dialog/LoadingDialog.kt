package com.light.xhd.ui.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.android.architecture.extension.empty
import com.android.architecture.ui.page.BaseDialog
import com.light.xhd.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/13
 * Modify date: 2022/8/13
 * Version: 1
 */
class LoadingDialog {

    @Suppress("UNCHECKED_CAST")
    class Builder<B : Builder<B>>(context: Context) : BaseDialog.Builder<B>(context) {

        private val tvMessage: TextView

        init {
            setContentView(R.layout.dialog_loading)
            setAnimStyle(BaseDialog.ANIM_TOAST)
            setBackgroundDimEnabled(false)
            setCancelable(false)
            tvMessage = findViewById(R.id.tv_wait_message)
        }

        fun message(text: String): B {
            tvMessage.text = text
            tvMessage.visibility = if (text.empty) View.GONE else View.VISIBLE
            return this as B
        }

    }
}