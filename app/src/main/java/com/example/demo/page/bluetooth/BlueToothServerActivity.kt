package com.example.demo.page.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.example.demo.R
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.activity_blue_server.*
import java.io.IOException
import java.io.InputStream


/**
 *蓝牙服务端
 */
class BlueToothServerActivity : FrameActivity() {


    private lateinit var mServerSocket: BluetoothServerSocket
    private lateinit var mSocket: BluetoothSocket
    private lateinit var adapter: BluetoothAdapter
    private val SERVSE_NAME = "blueTestServer"
    private val STATUS_WAIT = 1
    private val STATUS_CONTENT = 2
    private val STATUS_ACCEPT = 3


    private var handler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val content = msg?.obj.toString()
            when (msg?.what) {
                STATUS_CONTENT ->
                    showContent(content)
                STATUS_ACCEPT ->
                    showContent(content)
                STATUS_WAIT ->
                    showContent(content)
            }
        }
    }

    override fun initData() {
        super.initData()
        adapter = BluetoothAdapter.getDefaultAdapter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_blue_server
    }

    override fun initListener() {
        super.initListener()

        btn_open_server.setOnCheckedChangeListener { isOpen, _ ->

            if (!isOpen) {
                return@setOnCheckedChangeListener
            }

            Thread {
                mServerSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(SERVSE_NAME,
                    BlueToothUserActivity.SPP_UUID)

                val msg = Message()
                msg.obj = "请稍后,正在等待客户端连接..."
                msg.what = STATUS_WAIT
                handler.sendMessage(msg)
                mSocket = mServerSocket.accept()

                val msg2 = Message()
                msg2.obj = "已经连接上啦！"
                msg2.what = STATUS_CONTENT
                handler.sendMessage(msg2)

                //启动接收服务器线程
                onStartRead()

            }.start()
        }
    }

    fun onStartRead() {
        Thread {
            val buffer = ByteArray(1024)
            var bytes: Int
            var inputStream: InputStream? = null
            try {
                inputStream = mSocket.getInputStream()
                while (true) {
                    if (inputStream.read(buffer).also { bytes = it } > 0) {
                        val buf_data = ByteArray(bytes)
                        for (i in 0 until bytes) {
                            buf_data[i] = buffer[i]
                        }
                        val s = String(buf_data)
                        val msg = Message()
                        msg.obj = s
                        msg.what = STATUS_ACCEPT
                        handler.sendMessage(msg)
                        //同样  子线程不能刷新UI
//                        showContent(s)
                    }
                }
            } catch (e1: IOException) {
                e1.printStackTrace()
            } finally {
                try {
                    inputStream!!.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }

        }.start()
    }


    fun showContent(content: String) {
        runOnUiThread {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        }
    }
}
