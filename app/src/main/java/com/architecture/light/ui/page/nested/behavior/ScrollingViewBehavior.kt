package com.architecture.light.ui.page.nested.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/7
 * Modify date: 2022/11/7
 * Version: 1
 */
class ScrollingViewBehavior(context: Context, attrs: AttributeSet) :
    HeaderScrollingViewBehavior(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is TextView
    }
}