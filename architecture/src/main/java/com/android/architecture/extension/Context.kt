package com.android.architecture.extension

import android.content.Context
import androidx.core.content.ContextCompat

//屏幕宽度(px)
inline val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

//屏幕高度(px)
inline val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

//屏幕的密度
inline val Context.density: Float
    get() = resources.displayMetrics.density

//dp 转为 px
fun Context.dp2px(value: Int): Int = (density * value + 0.5).toInt()

//dp 转为 px
fun Context.dp2px(value: Float): Int = (density * value + 0.5).toInt()

//px 转为 dp
fun Context.px2dp(value: Int): Float = value.toFloat() / density

// 获取系统服务
fun <S> getSystemService(serviceClass: Class<S>): S? {
    return ContextCompat.getSystemService(getContext(), serviceClass)
}