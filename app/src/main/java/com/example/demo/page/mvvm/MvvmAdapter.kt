package com.example.demo.page.mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.demo.R


/**

@author dengfeijie
@description:
@date : 2020/7/23 16:08
 */
class MvvmAdapter(val context: Context, val list: List<User>) :
    RecyclerView.Adapter<MvvmAdapter.BindingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder = BindingViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.item_mvvm_layout, parent, false))


    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val user = list[position]
        holder.mBinding.setVariable(BR.Item, user)
        holder.mBinding.executePendingBindings()
    }

    override fun getItemCount(): Int =
        list.size

    class BindingViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding
    }


}