package com.architecture.sample.ui.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.android.architecture.ui.page.BaseDialog

/**
 * Created by SuQi on 2022/8/10.
 * Describe:
 */
class CommonDialog {

    class Builder<B : Builder<BaseDialog.Builder<*>>>(context: Context?) :
        BaseDialog.Builder<B>(context) {

        private var mAutoDismiss = true
        private val mContainerLayout: ViewGroup
        private val mTitleView: TextView
        private val mCancelView: TextView
        private val mLineView: View
        private val mConfirmView: TextView
        fun setCustomView(@LayoutRes id: Int): B {
            return setCustomView(LayoutInflater.from(context).inflate(id, mContainerLayout, false))
        }

        fun setCustomView(view: View?): B {
            mContainerLayout.addView(view, 1)
            return this as B
        }

        fun setTitle(@StringRes id: Int): B {
            return setTitle(getString(id))
        }

        fun setTitle(text: CharSequence?): B {
            mTitleView.text = text
            return this as B
        }

        fun setCancel(@StringRes id: Int): B {
            return setCancel(getString(id))
        }

        fun setCancel(text: CharSequence?): B {
            mCancelView.text = text
            mLineView.visibility =
                if (text == null || "" == text.toString()) View.GONE else View.VISIBLE
            return this as B
        }

        fun setConfirm(@StringRes id: Int): B {
            return setConfirm(getString(id))
        }

        fun setConfirm(text: CharSequence?): B {
            mConfirmView.text = text
            return this as B
        }

        fun setAutoDismiss(dismiss: Boolean): B {
            mAutoDismiss = dismiss
            return this as B
        }

        fun autoDismiss() {
            if (mAutoDismiss) {
                dismiss()
            }
        }

        init {
            setContentView(R.layout.ui_dialog)
            setAnimStyle(BaseDialog.ANIM_IOS)
            setGravity(Gravity.CENTER)
            mContainerLayout = findViewById(R.id.ll_ui_container)
            mTitleView = findViewById(R.id.tv_ui_title)
            mCancelView = findViewById(R.id.tv_ui_cancel)
            mLineView = findViewById(R.id.v_ui_line)
            mConfirmView = findViewById(R.id.tv_ui_confirm)
            setOnClickListener(mCancelView, mConfirmView)
        }
    }

}