package com.android.architecture.extension

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.android.architecture.utils.AppUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
fun getContext(): Context = AppUtils.getApp().applicationContext

fun getResources(): Resources = getContext().resources

fun getString(@StringRes id: Int) = getContext().getString(id)

fun getString(@StringRes id: Int, vararg args: Any) = getResources().getString(id, args)

fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(getContext(), id)

fun getColor(@ColorRes id: Int) = ContextCompat.getColor(getContext(), id)