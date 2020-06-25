package com.example.demo.page.base

import android.os.Bundle
import android.os.PersistableBundle

/**

@author dengfeijie
@description:
@date : 2020/6/9 12:34
 */
open class FrameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData() {

    }

    override fun getLayoutId(): Int {
        return -1
    }


    override fun initListener() {

    }

}