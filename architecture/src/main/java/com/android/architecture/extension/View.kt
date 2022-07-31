package com.android.architecture.extension

import android.view.View

fun <T : View> T.click(block: (T) -> Unit) {
    setOnClickListener {
        block(this)
    }
}