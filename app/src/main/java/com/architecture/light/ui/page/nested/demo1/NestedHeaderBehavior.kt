package com.architecture.light.ui.page.nested.demo1

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/31
 * Modify date: 2022/10/31
 * Version: 1
 */
class NestedHeaderBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        // child: 当前 Behavior 所关联的 View，此处是 HeaderView
        // dependency: 待判断是否需要监听的其他子 View
        return dependency.id == R.id.content_layout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.translationY = dependency.translationY * 0.5f
        child.alpha = 1 + dependency.translationY / (child.height * 0.6f)
        return true
    }

}