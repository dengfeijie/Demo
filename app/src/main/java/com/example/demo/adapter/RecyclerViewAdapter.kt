package com.example.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.item_img_view.view.*
import kotlinx.android.synthetic.main.item_text_view.view.*

/**

@author dengfeijie
@description:
@date : 2020/3/8 16:44
 */
class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private val TYPE_1 = 0
    private val TYPE_2 = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        var viewHolder : ViewHolder
        if (viewType == TYPE_1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_text_view, parent, false)
            viewHolder = ViewHolderText(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_img_view, parent, false)
            viewHolder = ViewHolderImage(view)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 20;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ViewHolderImage) {
            holder.itemView.iv_image.setImageResource(R.drawable.ic_launcher_background)
        } else {
            holder.itemView.tv_content.text = "内容${position}"
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) TYPE_1 else TYPE_2
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    inner class ViewHolderText(itemView: View) : ViewHolder(itemView) {
    }

    inner class ViewHolderImage(itemView: View) : ViewHolder(itemView) {

    }
}