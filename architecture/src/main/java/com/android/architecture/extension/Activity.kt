package com.android.architecture.extension

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.openActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    block.invoke(intent)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.openActivity() {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.openActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    block.invoke(intent)
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.openActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}