package com.example.demo.page.mvvm

/**
 * @author dengfeijie
 * @description: Model
 * @date : 2020/7/23 11:51
 */
class MvvmModel {

    val list = arrayListOf<User>()
    fun getData(): List<User> {
        repeat(10) {
            list.add(User("测试数据${it}", it))
        }
        return list
    }
}