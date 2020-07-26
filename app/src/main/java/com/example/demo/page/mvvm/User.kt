package com.example.demo.page.mvvm

import android.database.Observable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**

@author dengfeijie
@description:
@date : 2020/7/22 17:38
 */

class User(n: String, a: Int) : BaseObservable() {

    @get:Bindable
    var name = n
        set(value) {
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var age = a
        set(value) {
            notifyPropertyChanged(BR.age)
        }
}