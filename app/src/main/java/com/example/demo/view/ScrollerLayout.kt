package com.example.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller
import androidx.core.view.ViewConfigurationCompat

/**
 * @author dengfeijie
 * @description:
 * @date : 2020/3/28 15:22
 */
@Suppress("UNREACHABLE_CODE")
class ScrollerLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    /**
     * 用户滚动操作的实例
     */
    private var mScroller: Scroller


    /**
     *判定为拖动的最小移动像素
     */
    private val mTouchSlop: Int


    /**
     *手机按下时坐标
     */
    private var mXDown = 0f


    /**
     *  手机当时所处的屏幕坐标
     */
    private var mXMove = 0f


    /**
     * 上次触发ACTION_MOVE 时间时的屏幕坐标
     */
    private var mXLastMove = 0f


    /**
     * 界面可滚动的左边界
     */
    private var leftBorder = 0


    /***
     * 界面可滚动的右边界
     */
    private var rightBorder = 0


    init {
        //创建实例
        mScroller = Scroller(context)

        //获取mTouchSlop值
        mTouchSlop =
            ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context))
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = childCount

        for (i in 0 until count) {
            val childView = getChildAt(i)
            //为每一个子控件测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            val count = childCount
            for (i in 0 until count) {
                val childView = getChildAt(i)

                //为Scroller的子控件在水平方向上布局
                childView.layout(
                    i * childView.measuredWidth,
                    0,
                    (i + 1) * childView.measuredWidth,
                    childView.measuredHeight
                )
            }
            //初始化左右布局
            leftBorder = getChildAt(0).left
            rightBorder = getChildAt(count - 1).right
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mXDown = ev.rawX
                mXLastMove = mXDown
            }
            MotionEvent.ACTION_MOVE -> {
                mXMove = ev.rawX
                val diff = Math.abs(mXMove - mXDown)
                mXLastMove = mXMove
                //当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                mXMove = event.rawX
                val scrollerX = mXLastMove - mXMove
                if (scrollX + scrollerX < leftBorder) {
                    scrollTo(leftBorder, 0)
                    return true
                } else if (scrollX + width + scrollerX > rightBorder) {
                    scrollTo(rightBorder - width, 0)
                    return true
                }
                scrollBy(scrollerX.toInt(), 0)
                mXLastMove = mXMove
            }
            MotionEvent.ACTION_UP -> {
                //当手指抬起时，根据当前控件值来判定应该滚动到哪个子控件界面
                val targetIndex = (scrollX + width / 2) / width
                val dx = targetIndex * width - scrollX
                //第二步，调用startScroll方法滚动并刷新界面

                mScroller.startScroll(scrollX, 0, dx, 0)
                invalidate();
            }
        }
        return super.onTouchEvent(event)
    }


    override fun computeScroll() {

        //第三步，重写computeScroll（）方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }
}