package com.example.demo.page

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_time_demo.*
import java.util.*

class ProgressDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_demo)
        cp_progress_bar.visibility = View.VISIBLE
        val temp = Random().nextInt(800)
        val handle = Handler()
        handle.postDelayed({
            cp_progress_bar.visibility = View.GONE
            iv_image.setImageResource(R.drawable.mao)
            tv_test.text = "测试对否闪动"
        }, temp.toLong())
    }
}