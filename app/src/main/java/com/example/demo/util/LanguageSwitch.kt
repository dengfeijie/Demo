package com.example.demo.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import android.provider.MediaStore.Video.VideoColumns.LANGUAGE
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.example.demo.MainActivity
import java.util.*


class LanguageSwitch {

    companion object {
        /**
         * 更改应用语言
         *
         * @param context
         * @param locale 语言地区
         * @param isRestart 是否跳转
         */

        @RequiresApi(Build.VERSION_CODES.N)
        fun onSwitchLanguage(context: Context, ll: Locale, isRestart: Boolean) {
            val config =
                context.resources.configuration //获取系统的配置
            config.locales = LocaleList(ll)//将语言更改为繁体中文
            val resources = context.resources
            val metrics: DisplayMetrics = resources.displayMetrics
            val configuration: Configuration = resources.configuration
            configuration.locales = config.locales
            resources.updateConfiguration(configuration, metrics)
            saveLanguageSetting(context, ll)



            if (isRestart) {
                //开始新的activity同时移除之前所有的activity
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }

        val name: String = "_${LANGUAGE}"

        /**
         * sp保存语言
         */
        fun saveLanguageSetting(context: Context, locale: Locale) {

            val preferences: SharedPreferences =
                context.getSharedPreferences(name, Context.MODE_PRIVATE)
            preferences.edit().putString(LANGUAGE, locale.language).apply()
            preferences.edit().putString(COUNTRY, locale.country).apply()
        }
    }
}