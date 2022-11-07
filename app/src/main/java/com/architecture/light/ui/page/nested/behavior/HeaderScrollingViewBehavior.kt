package com.architecture.light.ui.page.nested.behavior

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat

/**
 * File describe:用于测量对应控件的高度和布局 需要配合{@link NestedHeaderBehavior}使用
 * Author: SuQi
 * Create date: 2022/11/7
 * Modify date: 2022/11/7
 * Version: 1
 */
private const val TAG = "HeaderScrolling"

open class HeaderScrollingViewBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private val mTempRect1 = Rect()
    private val mTempRect2 = Rect()

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        // 获取当前滚动控件的测量模式
        val childLpHeight = child.layoutParams.height
        //只有当前滚动控件为match_parent/wrap_content时才重新测量其高度，因为固定高度不会出现底部空白的情况
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            val dependencies = parent.getDependencies(child)
            val header = findFirstDependency(dependencies)
            if (header != null) {
                if (ViewCompat.getFitsSystemWindows(header)
                    && !ViewCompat.getFitsSystemWindows(child)
                ) {
                    // If the header is fitting system windows then we need to also,
                    // otherwise we'll get CoL's compatible measuring
                    ViewCompat.setFitsSystemWindows(child, true)
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        // If the set succeeded, trigger a new layout and return true
                        child.requestLayout()
                        return true
                    }
                }
                //获取当前父控件中可用的距离，
                var availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
                if (availableHeight == 0) {

                    // If the measure spec doesn't specify a size, use the current height
                    availableHeight = parent.height
                }
                //计算当前滚动控件的高度
                val height = availableHeight - header.measuredHeight + getScrollRange(header)
                val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    height,
                    if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT) View.MeasureSpec.EXACTLY else View.MeasureSpec.AT_MOST
                )
                //测量当前滚动的View的正确高度
                parent.onMeasureChild(
                    child,
                    parentWidthMeasureSpec,
                    widthUsed,
                    heightMeasureSpec,
                    heightUsed
                )
                return true
            }
        }
        return false
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        val dependencies = parent.getDependencies(child)
        val header = findFirstDependency(dependencies)
        if (header != null) {
            val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
            val available = mTempRect1
            // 得到下方控件的坐标
            available.set(
                parent.paddingLeft + layoutParams.leftMargin,
                header.bottom + layoutParams.topMargin,
                parent.width - parent.paddingRight - layoutParams.rightMargin,
                parent.height + header.bottom - parent.paddingBottom - layoutParams.bottomMargin
            )
            //拿到上面计算的坐标后，根据当前控件在父控件中设置的gravity,重新计算并得到控件在父控件中的坐标
            val out = mTempRect2
            GravityCompat.apply(
                resolveGravity(layoutParams.gravity),
                child.measuredWidth,
                child.measuredHeight,
                available,
                out,
                layoutDirection
            )
            //拿到坐标后重新布局
            child.layout(out.left, out.top, out.right, out.bottom)
        } else {
            //如果没有依赖，则调用父控件来处理布局
            parent.onLayoutChild(child, layoutDirection)
        }
        return true
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        offsetChildAsNeeded(parent, child, dependency)
        return true
    }

    private fun offsetChildAsNeeded(parent: CoordinatorLayout, child: View, dependency: View) {
        val behavior = (dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior is NestedHeaderBehavior) {
            Log.i(
                TAG,
                "offsetChildAsNeeded: " + dependency.bottom + "--->" + child.top + "---->" + behavior.getOffset()
            )
            if (dependency.bottom == 0 && child.top == 0 && behavior.getOffset() == 0) {

            } else {
                ViewCompat.offsetTopAndBottom(
                    child,
                    dependency.bottom - child.top + behavior.getOffset()
                )
            }
        }
    }

    /**
     * 矫正当前Gravity
     */
    private fun resolveGravity(gravity: Int): Int {
        return if (gravity == Gravity.NO_GRAVITY) GravityCompat.START or Gravity.TOP else gravity
    }

    /**
     * 获取当前View的滑动范围，一般情况下，为view的高度。
     * 特殊情况下，滚动范围会小于View的高度。这种一般都是折叠布局
     *
     * @param v
     * @return
     */
    private fun getScrollRange(view: View): Int {
        return view.measuredHeight
    }

    /**
     * 从依赖集合中获取第一个
     */
    private fun findFirstDependency(views: List<View>): View? {
        if (views.isNotEmpty()) {
            return views[0]
        }
        return null
    }

}