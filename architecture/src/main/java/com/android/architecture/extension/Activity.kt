package com.android.architecture.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.architecture.extension.property.ActivityArgumentProperty
import com.android.architecture.extension.property.LifecycleAwareViewBinding

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

fun Activity.hideSoftInput() {
    val manager =
        com.android.architecture.extension.getSystemService(InputMethodManager::class.java)
    manager?.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * Activity的ViewBinding初始化
 */
inline fun <reified V : ViewBinding> Activity.binding(): LifecycleAwareViewBinding<AppCompatActivity, V> {
    val method = V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java
    )
    return LifecycleAwareViewBinding { method.invoke(null, layoutInflater) as V }
}

/**
 * Activity参数的获取
 */
fun <T> Activity.argument(defaultValue: T? = null) = ActivityArgumentProperty(defaultValue)