package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.bean.MessageBean

/**

@author dengfeijie
@description:
@date : 2020/5/17 15:43
 */

sealed class BaseVH(view: View) : RecyclerView.ViewHolder(view)


class MineMsgVH(view: View) : BaseVH(view) {
    val tvContentRight: TextView? = view.findViewById(R.id.tv_right_content)
}

class ReceiveMsgVH(view: View) : BaseVH(view) {
    val tvContentLeft: TextView? = view.findViewById(R.id.tv_left_content)
}


class MessageAdapter(val list: ArrayList<MessageBean>) : RecyclerView.Adapter<BaseVH>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseVH = if (viewType == MessageBean.LEFT_MSG) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_left,
            parent, false)
        ReceiveMsgVH(view)
    } else {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_right,
            parent, false)
        MineMsgVH(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        val msg = list.get(position)
        when (holder) {
            is MineMsgVH -> {
                holder.tvContentRight?.text = msg.msg
            }
            is ReceiveMsgVH -> {
                holder.tvContentLeft?.text = msg.msg
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list.get(position).type
    }
}