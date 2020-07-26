package com.example.demo.page.news_mvvm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.demo.api.ApiServices
import com.example.demo.app.DemoApp
import com.example.demo.bean.NewsBean
import com.example.demo.util.HttpUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**

@author dengfeijie
@description:   M  仓库层
@date : 2020/7/25 17:20
 */
object NewRepository {

    fun getNewsData(type: String, appkey: String): MutableLiveData<NewsBean> {

        val liveData = MutableLiveData<NewsBean>()
        val apiNews = HttpUtils.createServer(ApiServices::class.java)
        apiNews.getNewsData(type, appkey)
            .subscribeOn(Schedulers.newThread()) //  将被观察者事件源切换到子线程中去执行
            .observeOn(AndroidSchedulers.mainThread())  //每添加一个，就会在当前处切换Thread，直到有另一个observeOn
            .subscribe(object : Observer<NewsBean> {
                override fun onNext(t: NewsBean) {
                    liveData.value = t
                }

                override fun onError(e: Throwable) {
                    Log.d("ERROR", "onError: $e")
                    Toast.makeText(DemoApp.context, "获取失败", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
        return liveData
    }
}