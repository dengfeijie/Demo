package com.example.demo.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.item_blue_layout.view.*

/**
 * @author dengfeijie
 * @description:
 * @date : 2020/6/11 21:27
 */
class BlueListAdapter(
    private val context: Context,
    private var list: MutableList<BluetoothDevice>
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private var onSelectListener: OnSelectListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_blue_layout, null, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.itemView.tv_blue_name.text = list[position].name
        holder.itemView.tv_status.text =
            if (list[position].bondState == BluetoothDevice.BOND_NONE) {
                "未配对"
            } else {
                "已配对"
            }
        holder.itemView.setOnClickListener {
            onSelectListener?.onSelected(position)
        }
    }

    class MyViewHolder(
        view: View
    ) : RecyclerViewAdapter.ViewHolder(view)


    fun setOnSelectedListener(onSelectListener: OnSelectListener) {
        this.onSelectListener = onSelectListener
    }

    interface OnSelectListener {
        fun onSelected(position: Int)
    }
}