package com.example.demo.page.first_code.lifecycles

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**

@author dengfeijie
@description:  监听activity的生命周期
@date : 2020/7/4 23:39
 */
class MyLifecycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityStart() {
        Log.e("TAG", "activity启动了")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityDestroy() {
        Log.e("TAG", "activity销毁了")
    }
}