package com.android.architecture.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun <T : View> T.click(block: (T) -> Unit) {
    setOnClickListener {
        block(this)
    }
}

/**
 * 显示软键盘，需要先 requestFocus 获取焦点，如果是在 Activity Create，那么需要延迟一段时间
 */
fun View.showKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

/**
 * 隐藏软键盘
 */
fun View.hideKeyboard() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.showSoftInput(this, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * 切换软键盘
 */
fun View.toggleSoftInput() {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.toggleSoftInput(0, 0)
}

fun View.measuredView(width: Int, height: Int) {
    layout(0, 0, width, height)
    val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
    val measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    measure(measuredWidth, measuredHeight)
    layout(0, 0, this.measuredWidth, this.measuredHeight)
}