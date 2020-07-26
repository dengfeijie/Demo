package com.example.demo.page.news_mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**

@author dengfeijie
@description:
@date : 2020/7/26 0:01
 */
class MainViewModelFactory(val appkey: String) : ViewModelProvider.Factory {

    //返回一个viewModel对象
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(appkey) as T
    }
}