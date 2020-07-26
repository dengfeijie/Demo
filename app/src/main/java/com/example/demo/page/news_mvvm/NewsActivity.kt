package com.example.demo.page.news_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.app.DemoApp
import com.example.demo.bean.NewsBean
import com.example.demo.constant.AppConstant
import kotlinx.android.synthetic.main.activity_news.*

/**
 * 新闻的界面     V层
 */
class NewsActivity : AppCompatActivity() {

    private var dataList = arrayListOf<NewsBean.Result.Data>()
    private lateinit var newsAdapter: NewsAdapter

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        val mainViewModel =
            ViewModelProvider(this,
                MainViewModelFactory(AppConstant.appKey)).get(MainViewModel::class.java)

        rv_news.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(this, dataList)
        rv_news.adapter = newsAdapter


        //这里传入值,调用也是此代码
        mainViewModel.getNews("top")

        //这里观察newsBean,发生变化后将执行onChange的代码
        mainViewModel.newsBean.observe(this, Observer {

            dataList.addAll(it.result.data)
            newsAdapter.notifyDataSetChanged()
//            val list = it.result.data
//            repeat(list.size) {
//                Log.e("NewsIfo", list[it].title)
//            }
        })

    }
}
