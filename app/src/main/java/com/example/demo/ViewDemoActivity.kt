package com.example.demo.page

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_view_demo.*


/**
 * 该界面为自定义View类
 */
class ViewDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_demo)

        val lp = si_image_view.layoutParams

        sb_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                lp.width = progress * 10
                lp.height = progress * 10
                si_image_view.layoutParams = lp
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}
