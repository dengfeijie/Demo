package com.example.demo.page.first_code.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**

@author dengfeijie
@description:
@date : 2020/7/4 21:08
 */
class MainViewModelFactory(private val count: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(count) as T
    }
}