package com.example.demo.page.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

/**

@author dengfeijie
@description:
@date : 2020/5/30 22:03
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.getLayoutId())
        this.initData()
        this.initListener()
    }

    override fun onResume() {
        super.onResume()
    }

    abstract fun initData()

    abstract fun getLayoutId(): Int

    abstract fun initListener()

}