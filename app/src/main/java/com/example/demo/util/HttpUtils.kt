package com.example.demo.util

import android.util.Log
import com.example.demo.BuildConfig
import com.example.demo.constant.AppConstant
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger


/**

@author dengfeijie
@description:   封装网络的调用
@date : 2020/7/25 17:29
 */
object HttpUtils {

    private lateinit var client: OkHttpClient

    fun <T> createServer(clazz: Class<T>): T {

       //日志拦截器
        val httpLoggingInterceptor = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.e("HTTP-MSG", message)
            }
        })
        //设置日志的等级
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        //构建网络参数
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

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
