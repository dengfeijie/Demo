package com.example.demo.page

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.bean.PayBean
import com.example.demo.bean.PayResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_json_demo.*
import org.json.JSONObject


class JsonDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json_demo)

        btn_json.setOnClickListener {
            val jsonStr =
                "{\\\"sign\\\":\\\"75DA71E1D66A3FABBC2D461553DDD21A\\\",\\\"prepay_id\\\":\\\"wx21193707778009303cd53f880c438f0000\\\",\\\"partner_id\\\":\\\"1600816793\\\",\\\"app_id\\\":\\\"wx233a8d8b4db13d82\\\",\\\"package_value\\\":\\\"Sign=WXPay\\\",\\\"time_stamp\\\":\\\"1598009827\\\",\\\"nonce_str\\\":\\\"1598009827831\\\"}"
            val jsonNews = jsonStr.replace("\\", "")
            val gsonBean = Gson()
            val result: PayResponse = gsonBean.fromJson(jsonNews, PayResponse::class.java)
            Log.e("TAG", result.appId)

            val jsonObject = JSONObject(jsonNews)
            val appId = jsonObject.getString("sign")
            Log.e("sign", appId)
            Toast.makeText(this, appId, Toast.LENGTH_SHORT).show()
        }
    }
}
