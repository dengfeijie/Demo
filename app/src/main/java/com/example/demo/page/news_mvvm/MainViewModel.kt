package com.example.demo.page.news_mvvm

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.demo.bean.NewsBean

/**

@author dengfeijie
@description:  VM层
@date : 2020/7/25 23:42
 */
class MainViewModel(private val appkey: String) : ViewModel() {



    private val newsType = MutableLiveData<String>()


    /**
     *    这里监听newType数据,当调getNews的时候发生数据改变时会触发观察着newType的SwitchMap代码
     *    然后调用网络请求，返回一个liveData
     */
    var newsBean =
        Transformations.switchMap(newsType) {
            NewRepository.getNewsData(it, appkey)
        }


    /**
     * 外部调用，更改newsType的值
     */
    fun getNews(type: String) {
        newsType.value = type
    }
}