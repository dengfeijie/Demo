package com.example.demo.api

import com.example.demo.bean.NewsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**

@author dengfeijie
@description:  api方法定义
@date : 2020/7/25 22:24
 */
interface ApiServices {

    @GET("toutiao/index")
    fun getNewsData(
        @Query("type") type: String,
        @Query("key") key: String
    ): Observable<NewsBean>
}