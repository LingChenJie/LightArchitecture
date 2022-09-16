package com.android.architecture.extension

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.android.architecture.helper.DelayHelper

fun <T : View> T.click(block: (T) -> Unit) {
    setOnClickListener {
        block(this)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun <T : View> T.touch(repeatTrigger: Boolean = false, block: (T) -> Unit) {
    var touchUp = false
    var downTime = 0L
    val delay = 100L
    val what = -999
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchUp = false
                isPressed = true
                downTime = System.currentTimeMillis()
                if (repeatTrigger) {
                    DelayHelper.sendDelayTask(what, delay, object : DelayHelper.Task {
                        override fun execute() {
                            if (!touchUp) {
                                block(this@touch)
                                DelayHelper.sendDelayTask(what, delay)
                            } else {
                                DelayHelper.removeTask(what)
                            }
                        }
                    })
                }
            }
            MotionEvent.ACTION_UP -> {
                touchUp = true
                isPressed = false
                if (repeatTrigger) {
                    if (System.currentTimeMillis() - downTime < delay) {
                        block(this@touch)
                    }
                } else {
                    block(this@touch)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                isPressed = false
            }
        }
        true
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