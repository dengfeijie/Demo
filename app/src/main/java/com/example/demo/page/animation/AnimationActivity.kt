package com.example.demo.page.animation

import android.view.View
import android.view.animation.AnimationUtils
import com.example.demo.R
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : FrameActivity() {

    override fun initData() {
        super.initData()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_animation
    }

    override fun initListener() {
        super.initListener()
        btn_view1.setOnClickListener {

            if (view.visibility == View.GONE) {
                view.visibility = View.VISIBLE
                view.animation = AnimationUtils.makeInAnimation(this, true)
            } else {
                view.visibility = View.GONE
                view.animation = AnimationUtils.makeOutAnimation(this, true)
            }
        }
    }
}
