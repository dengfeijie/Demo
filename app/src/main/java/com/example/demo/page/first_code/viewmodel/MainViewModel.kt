package com.example.demo.page.first_code.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**

@author dengfeijie
@description:  测试取某个值, 封装性原理（非ViewModel只能观察数据不能设置数据）
@date : 2020/7/4 21:06
 */
class MainViewModel(count: Int) : ViewModel() {

    val counts: LiveData<Int>
        get() = _counts

    private val _counts = MutableLiveData<Int>()

    init {
        _counts.value = count
    }

    fun plusOne() {
        val c = _counts.value ?: 0
        _counts.value = c + 1
    }

    fun clear() {
        _counts.value = 0
    }
}