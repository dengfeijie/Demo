package com.example.demo.util

import android.annotation.TargetApi
import android.graphics.drawable.GradientDrawable
import android.os.Build


/**

@author dengfeijie
@description:
@date : 2021/4/17 13:43
 */
class DrawableUtils {

    private fun DrawableUtils() {
        throw UnsupportedOperationException("Can not be instantiated.")
    }

    companion object {

        fun createRectangleDrawable(color: Int, cornerRadius: Float): GradientDrawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadius = cornerRadius
            gradientDrawable.setColor(color)
            return gradientDrawable
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun createRectangleDrawable(colors: IntArray?, cornerRadius: Float): GradientDrawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadius = cornerRadius
            gradientDrawable.colors = colors
            return gradientDrawable
        }

        fun createOvalDrawable(color: Int): GradientDrawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.OVAL
            gradientDrawable.setColor(color)
            return gradientDrawable
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun createOvalDrawable(colors: IntArray?): GradientDrawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.OVAL
            gradientDrawable.colors = colors
            return gradientDrawable
        }
    }
}