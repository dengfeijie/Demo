package com.example.demo.bean

import org.litepal.crud.LitePalSupport

/**

@author dengfeijie
@description:
@date : 2020/5/31 11:15
 */
class PersonInfoBean : LitePalSupport() {
    var id = 0
    var name = ""
    var age = -1
    var sex = ""
}