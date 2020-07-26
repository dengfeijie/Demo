package com.example.demo.page.mvvm

import android.app.Application
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.util.*

/**

@author dengfeijie
@description: viewModel层
@date : 2020/7/23 11:59
 */
class MvvmViewModel(val context: Application) : BaseObservable() {

    private val mvvmModel = MvvmModel()
    val list: MutableList<User> = mvvmModel.getData() as MutableList<User>

    @get:Bindable
    var title: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var age: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }


    fun getData(view: View) {
        val random = Random()
        title = mvvmModel.getData()[random.nextInt(9)].name
        age = mvvmModel.getData()[random.nextInt(9)].age
    }
}