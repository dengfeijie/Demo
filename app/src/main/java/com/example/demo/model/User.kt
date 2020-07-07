package com.example.demo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**

@author dengfeijie
@description:  Room数据库的实体类
@date : 2020/7/6 16:51
 */
@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}