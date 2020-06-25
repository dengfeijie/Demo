package com.example.demo.bean

/**

@author dengfeijie
@description:
@date : 2020/5/17 20:51
 */
class MessageBean(var msg: String?, var type: Int) {

    companion object{
        const val RIGHT_MSG = 1
        const val LEFT_MSG = 2
    }
}