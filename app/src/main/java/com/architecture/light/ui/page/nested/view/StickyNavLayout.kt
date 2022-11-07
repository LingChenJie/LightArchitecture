package com.architecture.light.ui.page.nested.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.architecture.light.R

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/4
 * Modify date: 2022/11/4
 * Version: 1
 */
class StickyNavLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs),
    NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)

    private lateinit var mTopView: View
    private lateinit var mNavView: View
    private lateinit var mViewPager: ViewPager

    // 父控件可以滚动的距离
    private var mCanScrollDistance = 0F
    // 滚动监听
    private var mScrollChangeListener: ((moveRatio: Float) -> Unit)? = null


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val hideTop = dy > 0 && scrollY < mCanScrollDistance
        val showTop = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)
        if (hideTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyUnconsumed < 0) {
            scrollBy(0, dyUnconsumed)
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedScrollingParentHelper.nestedScrollAxes
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTopView = findViewById(R.id.top_view)
        mNavView = findViewById(R.id.tab_layout)
        mViewPager = findViewById(R.id.view_pager)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val titleHeight = resources.getDimension(com.android.architecture.R.dimen.dp_48)
        mCanScrollDistance = mTopView.measuredHeight - titleHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutParams = mViewPager.layoutParams
        layoutParams.height = measuredHeight - mNavView.measuredHeight
        mViewPager.layoutParams = layoutParams
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun scrollTo(x: Int, y: Int) {
        var tempY = y
        if (y < 0) {
            tempY = 0
        }
        if (y > mCanScrollDistance) {
            tempY = mCanScrollDistance.toInt()
        }
        mScrollChangeListener?.invoke(tempY / mCanScrollDistance)
        if (scrollY != tempY) {
            super.scrollTo(x, tempY)
        }
    }

    fun setScrollChangeListener(listener: (moveRatio: Float) -> Unit) {
        mScrollChangeListener = listener
    }

}