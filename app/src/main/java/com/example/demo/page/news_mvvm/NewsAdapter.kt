package com.example.demo.page.news_mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.bean.NewsBean
import com.example.demo.databinding.ItemNewsLayoutBinding
import kotlinx.android.synthetic.main.item_news_layout.view.*

/**

@author dengfeijie
@description: news适配器，利用databanding
@date : 2020/7/26 11:14
 */
class NewsAdapter(val context: Context, private val dataList: List<NewsBean.Result.Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class NewsViewHolder(val newsLayoutBinding: ItemNewsLayoutBinding) :
        RecyclerView.ViewHolder(newsLayoutBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemNewsBinding =
            DataBindingUtil.inflate<ItemNewsLayoutBinding>(LayoutInflater.from(context),
                R.layout.item_news_layout, parent, false)
        return NewsViewHolder(itemNewsBinding)
    }

    override fun getItemCount() = dataList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemNewBinding = (holder as NewsViewHolder).newsLayoutBinding
        itemNewBinding.let {
            it.news = dataList[position]
            it.executePendingBindings()
            it.root.setOnClickListener {
                Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
            }
        }
    }
}