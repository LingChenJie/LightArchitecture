package com.architecture.light.ui.dialog

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.android.architecture.ui.page.BaseDialog
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/10
 * Modify date: 2022/11/10
 * Version: 1
 */
class TipsDialog {

    companion object {
        const val ICON_FINISH = R.drawable.tips_finish_ic
        const val ICON_ERROR = R.drawable.tips_error_ic
        const val ICON_WARNING = R.drawable.tips_warning_ic
    }

    class Builder(context: Context) : BaseDialog.Builder<Builder>(context),
        BaseDialog.OnShowListener {
        private val mMessageView: TextView by lazy { findViewById(R.id.tv_tips_message) }
        private val mIconView: ImageView by lazy { findViewById(R.id.iv_tips_icon) }

        private var mDuration = 2000L

        init {
            setContentView(R.layout.dialog_tips)
            setAnimStyle(BaseDialog.ANIM_TOAST)
            setBackgroundDimEnabled(false)
            setCancelable(false)

            addOnShowListener(this)
        }

        fun icon(@DrawableRes id: Int): Builder {
            mIconView.setImageResource(id)
            return this
        }

        fun duration(duration: Long): Builder {
            mDuration = duration
            return this
        }

        fun message(text: String): Builder {
            mMessageView.text = text
            return this
        }

        override fun create(): BaseDialog {
            // 如果显示的图标为空就抛出异常
            requireNotNull(mIconView.drawable) { "The display type must be specified" }
            // 如果内容为空就抛出异常
            require(!TextUtils.isEmpty(mMessageView.text.toString())) { "Dialog message not null" }
            return super.create()
        }

        override fun onShow(dialog: BaseDialog?) {
            // 延迟自动关闭
            postDelayed(mDuration) {
                if (!isShowing) {
                    return@postDelayed
                }
                dismiss()
            }
        }
    }

}