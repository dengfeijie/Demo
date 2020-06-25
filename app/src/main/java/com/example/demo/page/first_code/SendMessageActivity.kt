package com.example.demo.page.first_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.MessageAdapter
import com.example.demo.bean.MessageBean
import kotlinx.android.synthetic.main.activity_send_message.*

class SendMessageActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter
    var list = ArrayList<MessageBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        initData()
        if (!::adapter.isInitialized) {
            adapter = MessageAdapter(list)
        }
        rv_show_message.layoutManager = LinearLayoutManager(this)
        rv_show_message.adapter = adapter

        btn_send_message.setOnClickListener {
            val mag: String? = et_input.text.toString()
            list.add(MessageBean(mag, MessageBean.RIGHT_MSG))
            adapter.notifyItemInserted(list.size - 1)
            rv_show_message.scrollToPosition(list.size - 1)
            et_input.setText("")
        }
    }

    fun initData() {
        list.add(MessageBean("在吗", MessageBean.LEFT_MSG))
        list.add(MessageBean("在的", MessageBean.RIGHT_MSG))
        list.add(MessageBean("你有什么事情吗？", MessageBean.RIGHT_MSG))
        list.add(MessageBean("我想问一下什么时候开学", MessageBean.LEFT_MSG))
        list.add(MessageBean("19后开学呢", MessageBean.RIGHT_MSG))
        list.add(MessageBean("哦哦", MessageBean.LEFT_MSG))
    }
}
