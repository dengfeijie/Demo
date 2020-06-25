package com.example.demo.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*
import kotlin.math.abs

/**

@author dengfeijie
@description:  shader的应用
@date : 2020/6/17 17:04
 */
class GradientView(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {


    private var startX = 0f
    private var startY = 0f
    private var cx = 50f
    private var cy = 50f


    private var shader: Shader = LinearGradient(0f, 0f, 100f, 100f,
        Color.parseColor("#FF4181"), Color.parseColor("#3F51B5"),
        Shader.TileMode.CLAMP)

    private var paint = Paint()

    init {
        paint.shader = this.shader
        paint.isAntiAlias = true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        shader = LinearGradient(cx - 50, cy - 50, cx + 50f, cy + 50f,
            Color.parseColor(getRandColorCode()), Color.parseColor(getRandColorCode()),
            Shader.TileMode.CLAMP)
        paint.shader = shader

        canvas?.drawCircle(cx, cy, 50f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x.toInt().toFloat()
                startY = event.y.toInt().toFloat()
                invalidate()

            }
            MotionEvent.ACTION_MOVE -> {
                invalidate()
                val endX = event.x
                val endY = event.y
                if (abs(startX - endX) > 8 || abs(startY - endY) > 8) {
                    cx = event.x
                    cy = event.y
                    invalidate()
                }
            }
        }
        return true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureWidthAndHeight(widthMeasureSpec),
            measureWidthAndHeight(heightMeasureSpec))
    }

    private fun measureWidthAndHeight(size: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(size)
        val specSize = MeasureSpec.getSize(size)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = 200
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    /** 
         * 获取十bai六进制du的颜色zhi代码.例如dao  "#6E36B4" 
         * @return String 
         */

    fun getRandColorCode()
            : String {
        var r: String
        var g: String
        var b: String
        val random = Random()
        r = Integer.toHexString(random.nextInt(256)).toUpperCase()
        g = Integer.toHexString(random.nextInt(256)).toUpperCase()
        b = Integer.toHexString(random.nextInt(256)).toUpperCase()

        r = if (r.length == 1) {
            "0${r}"
        } else {
            r
        }

        g = if (g.length == 1) {
            "0${g}"
        } else {
            g
        }

        b = if (b.length == 1) {
            "0${b}"
        } else {
            b
        }

        return "#${r + g + b}"
    }
}