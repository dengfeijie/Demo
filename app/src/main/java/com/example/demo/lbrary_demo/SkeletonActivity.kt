package com.example.demo.lbrary_demo

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.util.DrawableUtils
import kotlinx.android.synthetic.main.activity_skeleton.*
import me.samlss.broccoli.Broccoli
import me.samlss.broccoli.BroccoliGradientDrawable
import me.samlss.broccoli.PlaceholderParameter


/**
 * 骨架屏库demo
 * 库地址：https://github.com/samlss/Broccoli
 */
class SkeletonActivity : AppCompatActivity() {
    val broccoli = Broccoli()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skeleton)
        //默认样式的
        //broccoli.addPlaceholders(tv_name, iv_phone, tv_location, tv_story)

        //添加自定义透明渐变动画占位图
        broccoli.addPlaceholder(getCustomAlphaPlace())
        //添加自定义的占位图
        broccoli.addPlaceholder(getCustomScalePlaceList())
        //添加渐变动画的占位图
        broccoli.addPlaceholder(getPlaceList())
        broccoli.show()
        clearBr()
    }


    /**
     * 创建放大动画
     */
    private fun createScaleAnimation() = ScaleAnimation(0.3f, 1F, 1F, 1F).run {
        duration = 600
        repeatMode = Animation.REVERSE
        repeatCount = Animation.INFINITE
        this
    }

    /**
     * 创建透明度渐变动画
     */
    private fun createAlphaAnimation() = AlphaAnimation(1.0f, 0.3f).run {
        duration = 600
        repeatMode = Animation.REVERSE
        repeatCount = Animation.INFINITE
        this
    }


    /***
     * 获取子控件占位图集合
     */
    private fun getPlaceList(): MutableList<PlaceholderParameter> {
        val childList = mutableListOf<PlaceholderParameter>()
        for (i in 0..cl_item.childCount) {
            childList.add(PlaceholderParameter.Builder()
                .setView(cl_item.getChildAt(i))
                .setDrawable(BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                    Color.parseColor("#CCCCCC"),
                    0F,
                    1000,
                    LinearInterpolator())
                ).build())
        }
        return childList
    }


    /**
     * 获取自定义的占位图
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCustomScalePlaceList(): MutableList<PlaceholderParameter> {

//        val colors = intArrayOf(R.color.place_bg,R.color.colorAccent)
        val childList = mutableListOf<PlaceholderParameter>()
        for (i in 0..cl_brief.childCount) {
            childList.add(PlaceholderParameter.Builder()
                .setView(cl_brief.getChildAt(i))
                .setAnimation(createScaleAnimation())
                .setDrawable(DrawableUtils.createRectangleDrawable(this.getColor(R.color.place_bg),
                    10F))
                .build())
        }
        return childList
    }


    /**
     * 获取自定义的透明度淡出淡入占位图
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCustomAlphaPlace(): PlaceholderParameter =
        PlaceholderParameter.Builder()
            .setView(iv_phone)
            .setAnimation(createAlphaAnimation())
            .setDrawable(DrawableUtils.createRectangleDrawable(this.getColor(R.color.gray),
                10F))
            .build()


    /**
     * 清空所有占位图
     *
     */
    private fun clearBr() {
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                broccoli.removeAllPlaceholders()
            }
        }.start()
    }
}
