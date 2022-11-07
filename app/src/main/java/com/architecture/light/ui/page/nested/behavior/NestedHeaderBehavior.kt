package com.architecture.light.ui.page.nested.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import java.lang.ref.WeakReference

/**
 * File describe: 处理嵌套滑动的Behavior,仿照{@link com.google.android.material.bottomsheet.BottomSheetBehavior}
 * Author: SuQi
 * Create date: 2022/11/7
 * Modify date: 2022/11/7
 * Version: 1
 */
private const val TAG = "NestedHeaderBehavior"

class NestedHeaderBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private lateinit var mNestedScrollingChildRef: WeakReference<View>
    private var mOffset = 0// 记录当前布局的偏移量

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        mNestedScrollingChildRef = WeakReference(findScrollingChild(parent))
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        //只拦截竖直方向
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        val scrollingChild = mNestedScrollingChildRef.get()
        if (target !== scrollingChild) {
            return
        }
        val currentTop = child.top
        val newTop = currentTop - dy
        if (dy > 0) {//向上滑动
            if (newTop >= -child.height) {
                Log.i(TAG, "onNestedPreScroll:向上移动currentTop--->$currentTop newTop--->$newTop")
                consumed[1] = dy
                mOffset = -dy
                ViewCompat.offsetTopAndBottom(child, -dy)
                coordinatorLayout.dispatchDependentViewsChanged(child)
            } else {//当超过后，单独处理
                consumed[1] = child.height + currentTop
                mOffset = -consumed[1]
                ViewCompat.offsetTopAndBottom(child, -consumed[1])
                coordinatorLayout.dispatchDependentViewsChanged(child)
            }
        }
        if (dy < 0) {// 向下滑动
            Log.i(TAG, "onNestedPreScroll:向下移动currentTop--->$currentTop newTop--->$newTop")
            if (newTop <= 0 && !target.canScrollVertically(-1)) {
                consumed[1] = dy
                mOffset = -dy
                ViewCompat.offsetTopAndBottom(child, -dy)
                coordinatorLayout.dispatchDependentViewsChanged(child)
            }
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyUnconsumed < 0) {//表示已经向下滑动到头
            val currentTop = child.top
            val newTop = currentTop - dyUnconsumed
            if (newTop <= 0) {
                Log.i(
                    TAG,
                    "onNestedScroll: dyUnconsumed--> $dyUnconsumed currentTop--->$currentTop newTop--->$newTop"
                )
                ViewCompat.offsetTopAndBottom(child, -dyUnconsumed)
                mOffset = -dyUnconsumed
            } else {//如果当前的值大于最大的偏移量，那么就直接滚动到-currentTop就行了
                ViewCompat.offsetTopAndBottom(child, -currentTop)
                mOffset = -currentTop
            }
            coordinatorLayout.dispatchDependentViewsChanged(child)
        }
    }


    /**
     * 获取实现了NestedScrollingChild或NestedScrollingChild2接口的View
     */
    private fun findScrollingChild(view: View): View? {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view
        }
        if (view is ViewGroup) {
            val group = view as ViewGroup
            var i = 0
            val count = group.childCount
            while (i < count) {
                val scrollingChild = findScrollingChild(group.getChildAt(i))
                if (scrollingChild != null) {
                    return scrollingChild
                }
                i++
            }
        }
        return null
    }

    /**
     * 获取当前控件的偏移量
     */
    fun getOffset(): Int {
        return mOffset
    }
}