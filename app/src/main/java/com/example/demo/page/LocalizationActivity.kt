package com.example.demo.page

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Video.VideoColumns.LANGUAGE
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.util.LanguageSwitch
import kotlinx.android.synthetic.main.activity_localization.*
import java.util.*


class LocalizationActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localization)


        //获取保存的语言信息
        val language =
            this.getSharedPreferences(LanguageSwitch.name, Context.MODE_PRIVATE)
                .getString(LANGUAGE, "")
        if (!TextUtils.isEmpty(language)) {
            when (language) {
                "zh" -> LanguageSwitch.onSwitchLanguage(this, Locale.CHINA, false)
                "en" -> LanguageSwitch.onSwitchLanguage(this, Locale.ENGLISH, false)
            }
        }


        button4.setOnClickListener { LanguageSwitch.onSwitchLanguage(this, Locale.ENGLISH, true) }
        button5.setOnClickListener { LanguageSwitch.onSwitchLanguage(this, Locale.CHINA, true) }
    }
}
