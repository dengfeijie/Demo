package com.example.demo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**

@author dengfeijie
@description:
@date : 2020/7/7 17:38
 */
@Entity
data class Book(var name: String, var pages: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}