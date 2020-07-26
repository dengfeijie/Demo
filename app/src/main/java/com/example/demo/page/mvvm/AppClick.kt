package com.example.demo.page.mvvm

import android.content.Context
import android.view.View
import android.widget.Toast

/**

@author dengfeijie
@description:  点击事件
@date : 2020/7/22 21:25
 */
class AppClick(val context: Context) {

    fun onClick(v: View) {
        Toast.makeText(context, "改变数据", Toast.LENGTH_SHORT).show()
    }
}