package com.architecture.light.ui.page.nested.demo1

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/31
 * Modify date: 2022/10/31
 * Version: 1
 */
class NestedContentBehavior(context: Context, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var headerHeight = 0
    private lateinit var contentView: View
    private var scroller: OverScroller? = null
    private val scrollerRunnable = object : Runnable {
        override fun run() {
            scroller?.let {
                if (it.computeScrollOffset()) {
                    contentView.translationY = it.currY.toFloat()
                    ViewCompat.postOnAnimation(contentView, this)
                }
            }
        }
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        contentView = child
        // 首先让父布局按照标准方式解析
        parent.onLayoutChild(child, layoutDirection)
        // 获取到 HeaderView 的高度
        headerHeight = parent.findViewById<View>(R.id.header_layout).measuredHeight
        // 设置 top 从而排在 HeaderView的下面
        ViewCompat.offsetTopAndBottom(child, headerHeight)
        return true// true 表示我们自己完成了解析 不要再自动解析了
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        // 如果是垂直滑动的话就声明需要处理
        // 只有这里返回 true 才会收到下面一系列滑动事件的回调
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
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
        stopAutoScroll()
//        // 只处理手指上滑
//        if (dy > 0) {
//            val newTranslationY = child.translationY - dy
//            if (newTranslationY >= -headerHeight) {
//                // 完全消耗滑动距离后没有完全贴顶或刚好贴顶
//                // 那么就声明消耗所有滑动距离，并上移 RecyclerView
//                consumed[1] = dy
//                child.translationY = newTranslationY
//            } else {
//                // 如果完全消耗那么会导致 RecyclerView 超出可视区域
//                // 那么只消耗恰好让 RecyclerView 贴顶的距离
//                consumed[1] = headerHeight + child.translationY.toInt()
//                child.translationY = -headerHeight.toFloat()
//            }
//        }
        val newTranslationY = child.translationY - dy
        if (dy > 0) {// 手指上滑
            if (newTranslationY >= -headerHeight) {
                child.translationY = newTranslationY
                consumed[1] = dy
            }
        } else {// 手指下滑
            if (newTranslationY <= 0 && !target.canScrollVertically(-1)) {
                child.translationY = newTranslationY
                consumed[1] = dy
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
        stopAutoScroll()
        // 此时 RV 已经完成了滑动，dyUnconsumed 表示剩余未消耗的滑动距离
        // 只处理手指向下滑动的情况
        if (dyUnconsumed < 0 && type == ViewCompat.TYPE_NON_TOUCH) {
            val newTranslationY = child.translationY - dyUnconsumed * 100000
            if (newTranslationY <= 0) {
                child.translationY = newTranslationY
            } else {
                child.translationY = 0f
            }
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        if (child.translationY >= 0f || child.translationY <= -headerHeight) {
            return
        }
        if (child.translationY <= -headerHeight * 0.5f) {
            stopAutoScroll()
            startAutoScroll(child.translationY.toInt(), -headerHeight, 1000)
        } else {
            stopAutoScroll()
            startAutoScroll(child.translationY.toInt(), 0, 600)
        }
    }

    private fun startAutoScroll(current: Int, target: Int, duration: Int) {
        if (scroller == null) {
            scroller = OverScroller(contentView.context)
        }
        if (scroller!!.isFinished) {
            contentView.removeCallbacks(scrollerRunnable)
            scroller!!.startScroll(0, current, 0, target - current, duration)
            ViewCompat.postOnAnimation(contentView, scrollerRunnable)
        }
    }

    private fun stopAutoScroll() {
        scroller?.let {
            if (!it.isFinished) {
                it.abortAnimation()
                contentView.removeCallbacks(scrollerRunnable)
            }
        }
    }

}