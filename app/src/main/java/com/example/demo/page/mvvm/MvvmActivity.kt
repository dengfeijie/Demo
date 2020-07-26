package com.example.demo.page.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.databinding.ActivityMvvmBinding
import kotlinx.android.synthetic.main.activity_mvvm.*


/**
 * mvvm架构
 */

class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvvmActivity =
            DataBindingUtil.setContentView<ActivityMvvmBinding>(this, R.layout.activity_mvvm)
        val user = User("邓飞杰", 22)
        mvvmActivity.user = user
        mvvmActivity.click = AppClick(this)
        val viewModel = MvvmViewModel(application)
        mvvmActivity.viewModel = viewModel

        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = MvvmAdapter(this, viewModel.list)
    }
}
