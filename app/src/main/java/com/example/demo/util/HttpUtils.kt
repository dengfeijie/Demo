package com.example.demo.util

import android.util.Log
import com.example.demo.constant.AppConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**

@author dengfeijie
@description:   封装网络的调用
@date : 2020/7/25 17:29
 */
object HttpUtils {

    private lateinit var client: OkHttpClient

    fun <T> createServer(clazz: Class<T>): T {

        //日志拦截器
        val intercepteor = HttpLoggingInterceptor { Log.e("HTTP-Utils", it) }


        //设置日志的等级
        intercepteor.level = HttpLoggingInterceptor.Level.BODY


        //构建网络参数
        val builder = OkHttpClient.Builder()
            .addInterceptor(intercepteor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        client = builder.build()


        //构造retrofit对象
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.BASE_API)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加RxJava
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        //返回retrofit对象
        return retrofit.create(clazz)
    }
}
