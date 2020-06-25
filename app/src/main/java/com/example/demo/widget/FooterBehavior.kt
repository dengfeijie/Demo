package com.example.demo.widget

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**

@author dengfeijie
@description:
@date : 2020/3/8 21:57
 */
class FooterBehavior(private var context: Context, private var attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>() {

    private var directionChange: Int = 0


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (ViewCompat.SCROLL_AXIS_VERTICAL and axes) != 0
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
        if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel()
            directionChange = 0
        }
        directionChange += dy
        if (directionChange > child.height && child.visibility == View.VISIBLE) {
            hide(child)
            Log.e("TAG", "text")

        } else if (directionChange < 0 && child.visibility == View.GONE) {
            Log.e("TAG", "text2")

            show(child)
        }

    }

    private fun hide(view: View) {
        var animator = view.animate().translationY(view.height.toFloat())
            .setInterpolator(FastOutSlowInInterpolator())
            .setDuration(200)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

    private fun show(view: View) {
        var animator = view.animate().translationY(0F)
            .setInterpolator(FastOutSlowInInterpolator())
            .setDuration(200)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }
}