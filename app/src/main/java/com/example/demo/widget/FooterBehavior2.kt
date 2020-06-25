package com.example.demo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

/**

@author dengfeijie
@description:       自定义Behavior
@date : 2020/3/9 22:45
 */
class FooterBehavior2(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    var currentValue = 0

    //当 dependency instanceof AppBarLayout 返回TRUE，将会调用onDependentViewChanged（）方法
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        //根据dependency top值的变化改变 child 的 translationY
//        val translationY = Math.abs(dependency.top).toFloat()
//        child.translationY = translationY

        if (dependency.top < currentValue) {
            child.translationY = Math.abs(dependency.top.toFloat())
//            child.visibility = View.GONE
            Log.e("TAG1","${currentValue}  = ${dependency.top}")

        } else if (dependency.top > currentValue) {
            child.translationY = 0f
//            child.visibility = View.VISIBLE

            Log.e("TAG2","${currentValue}  = ${dependency.top}")
        }

        currentValue = dependency.top

        return true
    }

}