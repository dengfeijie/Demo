package com.example.demo.app

import android.content.Context
import androidx.multidex.MultiDex
import org.litepal.LitePal.initialize
import org.litepal.LitePalApplication

/**
 * @author dengfeijie
 * @description:
 * @date : 2020/5/30 21:55
 */
class DemoApp : LitePalApplication() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
        initialize(this)
        context = applicationContext
    }
}