package com.example.demo

import android.os.Bundle
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.layout_test.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

/**

@author dengfeijie
@description: 协程的应用
@date : 2020/6/20 19:58
 */
class CoroutineDemo : FrameActivity() {

    private var string = ""
    override fun initData() {
        super.initData()
        GlobalScope.launch(Dispatchers.Main) {
            onConsumeTime()
            println(Thread.currentThread().name)
            tv_test.text = string
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.layout_test
    }

    private suspend fun onConsumeTime() {
        withContext(Dispatchers.IO) {
            repeat(10) {
                println(Thread.currentThread().name + it)
                string += "哈哈$it"
            }
        }
    }

}