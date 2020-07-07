package com.example.demo.page.first_code

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.demo.R
import com.example.demo.db_dao.BookDao
import com.example.demo.db_dao.UserDao
import com.example.demo.model.Book
import com.example.demo.model.User
import com.example.demo.page.base.FrameActivity
import com.example.demo.page.first_code.viewmodel.MainViewModel
import com.example.demo.page.first_code.viewmodel.MainViewModelFactory
import com.example.demo.util.RoomUtil
import com.example.demo.workmanager.SimpleWorker
import kotlinx.android.synthetic.main.activity_jet_pack_demo.*
import kotlin.concurrent.thread

class JetPackDemoActivity : FrameActivity(), LifecycleObserver {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var sp: SharedPreferences
    private val SP_KEY = "sp_key"
    private var userDao: UserDao? = null
    private var bookDao: BookDao? = null
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var book1: Book
    private lateinit var book2: Book


    override fun initData() {
        super.initData()

        lifecycle.addObserver(this)


        //Room数据库相关

        userDao = RoomUtil.getDatabase(this)?.userDao()
        bookDao = RoomUtil.getDatabase(this)?.bookDao()
        user1 = User("Tom", "dengfeijie", 22)
        user2 = User("jack", "songtie", 22)
        book1 = Book("《Android第一行代码", 300)
        book2 = Book("《Flutter第一行代码", 700)


        sp = getPreferences(Context.MODE_PRIVATE)
        val count = sp.getInt(SP_KEY, 0)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(count)).get(MainViewModel::class.java)

        mainViewModel.counts.observe(this, Observer {
            tv_info.text = it.toString()
            it.toString().showToast()

        })
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_jet_pack_demo
    }


    override fun initListener() {
        super.initListener()


        btn_add.setOnClickListener {
            mainViewModel.plusOne()
        }


        btn_clear.setOnClickListener {
            mainViewModel.clear()
        }


        btn_insert_data.setOnClickListener {
            thread {
                user1.id = userDao?.insertUser(user1)!!.toInt()
                user2.id = userDao?.insertUser(user2)!!.toInt()
            }
        }


        btn_query_data.setOnClickListener {
            thread {
                val list = userDao?.loadAllUsers()
                repeat(list?.size ?: 0) {
                    Log.e("TAG",
                        "曾用名：${list?.get(it)?.firstName} " +
                                "现用名：${list?.get(it)?.lastName} " +
                                "年龄${list?.get(it)?.age}")

                    "曾用名：${list?.get(it)?.firstName}现用名：${list?.get(it)?.lastName}年龄${list?.get(it)?.age}".showToast()
                }
            }
        }


        btn_delete_data.setOnClickListener {
            thread {
                userDao?.deleteUserByName("dengfeijie")
            }
        }


        btn_insert_book.setOnClickListener {
            thread {
                book1.id = bookDao?.insertBook(book1)!!
                book2.id = bookDao?.insertBook(book2)!!
            }
        }


        btn_query_book.setOnClickListener {
            thread {
                val list = bookDao?.loadAllBooks()
                repeat(list?.size ?: 0) {
                    Log.e("BookTAG", "书名：${list?.get(it)?.name} 页数：${list?.get(it)?.pages}")
                    "书名：${list?.get(it)?.name} 页数：${list?.get(it)?.pages}".showToast()
                }
            }
        }


        btn_work_manager.setOnClickListener {
            val result = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            WorkManager.getInstance(this).enqueue(result)
        }
    }


    /**
     * 扩展函数显示Toast
     *
     */
    private fun String.showToast(showTime: Int = Toast.LENGTH_SHORT) {
        runOnUiThread {
            Toast.makeText(this@JetPackDemoActivity, this, showTime).show()
        }
    }

    override fun onPause() {
        super.onPause()
        sp.edit().putInt(SP_KEY, mainViewModel.counts.value ?: 0).apply()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityStart() {
        Log.e("TAG", "activity启动了")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityDestroy() {
        Log.e("TAG", "activity销毁了")
    }
}
