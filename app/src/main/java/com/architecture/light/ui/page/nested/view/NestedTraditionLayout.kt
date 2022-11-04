package com.architecture.light.ui.page.nested.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.architecture.light.R
import kotlin.math.abs

/**
 * Created by SuQi on 2022/11/2.
 * Describe:传统处理嵌套滑动的方式，如果父控件拦截，根据传统事件分发机制，
 * 如果父控件确定拦截事件，那么在同一事件序列中，子控件是没有办法获取到事件，
 *
 * 在下面的例子中，如果是同一事件序列中滑动导致headView隐藏，那么除非手指抬起，不然子控件是不能响应事件的。
 */
private const val TAG = "NestedTraditionLayout"

class NestedTraditionLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var mHeadView: View
    private lateinit var mNavView: View
    private lateinit var mViewPager: ViewPager

    private var mHeadTopHeight = 0
    private var mLastY = 0
    private var isHeadHide = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        mHeadView = findViewById(R.id.iv_head_image)
        mNavView = findViewById(R.id.tab_layout)
        mViewPager = findViewById(R.id.view_pager)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeadTopHeight = mHeadView.measuredHeight
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action and MotionEvent.ACTION_MASK
        val y = ev.y.toInt()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onInterceptTouchEvent ACTION_DOWN y:$y")
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "onInterceptTouchEvent ACTION_MOVE y:$y")
                val dy = mLastY - y
                //如果父控件拦截，根据传统事件传递机制，如果父控件确定拦截事件，那么在同一事件序列中，子控件是没有办法获取到事件的。
                if (abs(dy) > ViewConfiguration.get(context).scaledTouchSlop) {
                    if (dy > 0 && !isHeadHide) {// 如果是向上滑，且当前headView没有隐藏，那么就拦截
                        //如果是向上滑，且当前headView没有隐藏，那么就拦截
                        Log.d(TAG, "onInterceptTouchEvent: 开始向上拦截")
                        return true
                    } else if (dy < 0 && isHeadHide) {// 如果是向下, 且将headView已经隐藏，那么就拦截
                        //如果是向下, 且将headView已经隐藏，那么就拦截
                        Log.d(TAG, "onInterceptTouchEvent: 开始向下拦截")
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onInterceptTouchEvent ACTION_UP y:$y")
            }
        }
        return super.onInterceptTouchEvent(ev)//不拦截事件，把事件让给子控件。
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action and MotionEvent.ACTION_MASK
        val y = event.y.toInt()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouchEvent ACTION_DOWN y:$y")
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "onTouchEvent ACTION_MOVE y:$y")
                val dy = mLastY - y
                if (abs(dy) > 0) {
                    scrollBy(0, dy)
                }
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onTouchEvent ACTION_UP y:$y")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //ViewPager修改后的高度= 总高度-导航栏高度
        val layoutParams = mViewPager.layoutParams
        layoutParams.height = measuredHeight - mNavView.measuredHeight
        mViewPager.layoutParams = layoutParams
        //当ViewPager修改高度后，重新开始测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun scrollTo(x: Int, y: Int) {
        var tempY = y
        if (y < 0) {
            tempY = 0
        }
        if (y > mHeadTopHeight) {
            tempY = mHeadTopHeight
        }
        super.scrollTo(x, tempY)
        isHeadHide = scrollY == mHeadTopHeight
    }

}