package com.example.demo.page.socket

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_socket.*
import java.io.*
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//class SocketActivity : FrameActivity() {


//    override fun initData() {
//        super.initData()
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_socket
//    }
//
//    override fun initListener() {
//        super.initListener()
//        btn_send_msg.setOnClickListener {
//            if (TextUtils.isEmpty(et_editText.text.toString())) {
//                Toast.makeText(this, "不能发送空消息~", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            Thread{
//                try {
//                    val socket = Socket("192.168.0.110", 63876)
//                    Log.e("TAG", "是否连接${socket.isConnected}")
//                    val outputStream = socket.getOutputStream()
//                    outputStream.write(et_editText.text.toString().toByteArray())
//                    outputStream.flush()
//                    socket.shutdownOutput()
//                    val inputStream = socket.getInputStream()
//
//                    //防止乱码
//                    val inputStreamReader = InputStreamReader(inputStream, "GBK")
//                    val bufferedReader = BufferedReader(inputStreamReader)
//                    val stringBuffer = StringBuffer()
//                    while (bufferedReader.readLine() != null) {
//                        stringBuffer.append(bufferedReader.readLine())
//                    }
//
//                    runOnUiThread {
//                        Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show()
//                    }
//
//
//                    socket.close()
//                    bufferedReader.close()
//                    inputStream.close()
//                    inputStreamReader.close()
//                    outputStream.close()
//
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }.start()
//        }
//    }
//}
class SocketActivity : AppCompatActivity() {
    private var printWriter: PrintWriter? = null
    private var `in`: BufferedReader? = null
    private var mExecutorService: ExecutorService? = null
    private var receiveMsg: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)
        mExecutorService = Executors.newCachedThreadPool()
    }

    fun connect(view: View?) {
        mExecutorService?.execute(connectService()) //在一个新的线程中请求 Socket 连接
    }

    fun send(view: View?) {
        val sendMsg = et_editText.text.toString()
        mExecutorService?.execute(sendService(sendMsg))
    }

    fun disconnect(view: View?) {
        mExecutorService?.execute(sendService("0"))
    }

    private inner class sendService(private val msg: String) : Runnable {
        override fun run() {
            printWriter?.println(msg)
        }
    }

    private inner class connectService : Runnable {
        override fun run() { //可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
            try {
                val socket = Socket(HOST, PORT) //步骤一
                socket.soTimeout = 60000
                printWriter = PrintWriter(BufferedWriter(OutputStreamWriter( //步骤二
                    socket.getOutputStream(), "UTF-8")), true)
                `in` = BufferedReader(InputStreamReader(socket.getInputStream(), "UTF-8"))
                receiveMsg()
            } catch (e: Exception) {
                Log.e(TAG, "connectService:" + e.message) //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
            }
        }
    }

    private fun receiveMsg() {
        try {
            while (true) {                                      //步骤三
                if (`in`!!.readLine().also { receiveMsg = it } != null) {
                    Log.d(TAG, "receiveMsg:$receiveMsg")
                    runOnUiThread {
//                        mTextView!!.text = """$receiveMsg${mTextView!!.text}""".trimIndent()
                        Toast.makeText(this, receiveMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "receiveMsg: ")
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "TAG"
        private const val HOST = "192.168.0.110"
        private const val PORT = 63876
    }
}
