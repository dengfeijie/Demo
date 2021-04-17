package com.example.demo


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.lbrary_demo.SkeletonActivity
import com.example.demo.page.*
import com.example.demo.page.animation.AnimationActivity
import com.example.demo.page.bluetooth.BlueToothServerActivity
import com.example.demo.page.bluetooth.BlueToothUserActivity
import com.example.demo.page.first_code.JetPackDemoActivity
import com.example.demo.page.materialdesign.MaterialDesignActivity
import com.example.demo.page.first_code.SendMessageActivity
import com.example.demo.page.news_mvvm.NewsActivity
import com.example.demo.page.socket.SocketActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_time.setOnClickListener(this);
        btn_view.setOnClickListener(this)
        btn_material_design.setOnClickListener(this)
        btn_localization.setOnClickListener(this)
        btn_scroller.setOnClickListener(this)
        btn_message.setOnClickListener(this)
        btn_lite_pal.setOnClickListener(this)
        btn_bluetooth.setOnClickListener(this)
        btn_bluetooth_server.setOnClickListener(this)
        btn_view_animation.setOnClickListener(this)
        btn_jetpack.setOnClickListener(this)
        btn_socket.setOnClickListener(this)
        btn_location.setOnClickListener(this)
        btn_mvvm.setOnClickListener(this)
        btn_skeleton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_time ->
                startActivity(Intent(this, ProgressDemoActivity::class.java))

            btn_view ->
                startActivity(Intent(this, ViewDemoActivity::class.java))

            btn_material_design ->
                startActivity(Intent(this, MaterialDesignActivity::class.java))

            btn_localization ->
                startActivity(Intent(this, LocalizationActivity::class.java))

            btn_scroller ->
                startActivity(Intent(this, ScrollerActivity::class.java))

            btn_message ->
                startActivity(Intent(this, SendMessageActivity::class.java))

            btn_lite_pal ->
                startActivity(Intent(this, LitePalActivity::class.java))

            btn_bluetooth ->
                startActivity(Intent(this, BlueToothUserActivity::class.java))

            btn_bluetooth_server ->
                startActivity(Intent(this, BlueToothServerActivity::class.java))

            btn_view_animation ->
                startActivity(Intent(this, AnimationActivity::class.java))

            btn_jetpack ->
                startActivity(Intent(this, JetPackDemoActivity::class.java))

            btn_socket ->
                startActivity(Intent(this, SocketActivity::class.java))

            btn_location ->
                startActivity(Intent(this, LocationActivity::class.java))

            btn_mvvm ->
                startActivity(Intent(this, NewsActivity::class.java))

            btn_skeleton ->
                startActivity(Intent(this, SkeletonActivity::class.java))
        }
    }
}
