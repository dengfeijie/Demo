package com.example.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

/**

@author dengfeijie
@description:
@date : 2020/6/23 17:10
 */
class DrawTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint()
    private val path = Path()

    init {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.parseColor("#FF0000")
        paint.style = Paint.Style.STROKE
        path.moveTo(10f, 10f)
//        path.lineTo(140f, 10f)
        path.addArc(10f, 10f, 500f, 500f, -90f, 90f)
        path.rLineTo(-100f, 180f)
        path.rLineTo(-50f, 100f)
        canvas?.drawPath(path, paint)
//        canvas?.drawText("测试画文字", 100f, 100f, paint)
        paint.textSize = 28f
        canvas?.drawTextOnPath("这是一个按路径去画的文字啊,这是一个按路径去画的文字啊", path, 50f, 10f, paint)
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
            result = 500
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

}
