package com.example.demo.app

import org.litepal.LitePal.initialize
import org.litepal.LitePalApplication

/**
 * @author dengfeijie
 * @description:
 * @date : 2020/5/30 21:55
 */
class DemoApp : LitePalApplication() {
    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }
}