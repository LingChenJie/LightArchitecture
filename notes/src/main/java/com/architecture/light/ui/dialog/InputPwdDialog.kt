package com.architecture.light.ui.dialog

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.android.architecture.extension.showKeyboard
import com.android.architecture.ui.page.BaseDialog
import com.android.architecture.ui.widget.view.RegexEditText
import com.architecture.light.R

class InputPwdDialog {

    class Builder(context: Context) : CommonDialog.Builder<Builder>(context),
        TextView.OnEditorActionListener, BaseDialog.OnShowListener {
        private val mInputView: RegexEditText by lazy { findViewById(R.id.tv_input_message) }

        private var confirmListener: ((content: String) -> Unit)? = null

        init {
            setCustomView(R.layout.dialog_input_pwd)
            mInputView.setOnEditorActionListener(this)
            addOnShowListener(this)

            onConfirm {
                val editable = mInputView.text
                confirmListener?.invoke(editable?.toString() ?: "")
            }
        }

        fun hint(text: String): Builder {
            mInputView.hint = text
            return this
        }

        fun content(text: String): Builder {
            mInputView.setText(text)
            val editable = mInputView.text ?: return this
            val index = editable.length
            if (index <= 0) {
                return this
            }
            mInputView.requestFocus()
            mInputView.setSelection(index)
            return this
        }

        fun setInputRegex(regex: String): Builder {
            mInputView.inputRegex = regex
            return this
        }

        fun onConfirm2(listener: ((content: String) -> Unit)): Builder {
            confirmListener = listener
            return this
        }

        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val editable = mInputView.text
                confirmListener?.invoke(editable?.toString() ?: "")
                dismiss()
                return true
            }
            return false
        }

        override fun onShow(dialog: BaseDialog?) {
            postDelayed(500) {
                mInputView.showKeyboard()
            }
        }
    }

}