package com.example.demo.page.materialdesign

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_material_design.*

class MaterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)
        init()
        setSupportActionBar(tb_tool_bar);
        ctl_toolBarLayout.title = "多啦A梦"
        rv_recycler_view.layoutManager = LinearLayoutManager(this)
        rv_recycler_view.adapter = RecyclerViewAdapter(this)
    }

    fun init() {
        if (Build.VERSION.SDK_INT >= 21) {
            var decorView = getWindow().getDecorView();
            var option =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
             View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
             View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT)
            getWindow().setStatusBarColor(Color.TRANSPARENT)
        }
    }

}
