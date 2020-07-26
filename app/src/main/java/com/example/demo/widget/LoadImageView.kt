package com.example.demo.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.demo.app.DemoApp

/**

@author dengfeijie
@description: 加载网络图片的控件类
@date : 2020/7/26 22:35
 */
class LoadImageView(context: Context, @Nullable attributeSet: AttributeSet) :
    AppCompatImageView(context, attributeSet) {


    companion object {
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        @JvmStatic
        fun setImageUrl(iv: ImageView, url: String) {
            Glide.with(DemoApp.context).load(url).into(iv)
        }
    }
}