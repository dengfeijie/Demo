package com.example.demo.page

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.demo.R
import com.example.demo.bean.PersonInfoBean
import com.example.demo.page.base.FrameActivity
import kotlinx.android.synthetic.main.activity_lite_pal.*
import org.litepal.LitePal
import org.litepal.extension.findFirst

class LitePalActivity : FrameActivity() {

    lateinit var infoBean: PersonInfoBean


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lite_pal)


        infoBean = if (LitePal.findFirst<PersonInfoBean>() == null) {
            PersonInfoBean()
        } else {
            LitePal.findFirst<PersonInfoBean>()
        }

        et_age.setText(if (infoBean.age > -1) {
            infoBean.age.toString()
        } else {
            ""
        })
        et_name.setText(if (!TextUtils.isEmpty(infoBean.name)) {
            infoBean.name
        } else {
            ""
        })
        et_sex.setText(if (!TextUtils.isEmpty(infoBean.sex)) {
            infoBean.sex
        } else {
            ""
        })
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_lite_pal
    }

    override fun onBackPressed() {
        super.onBackPressed()

        infoBean.age = if (TextUtils.isEmpty(et_age.text.toString())) {
            -1
        } else {
            et_age.text.toString().toInt()
        }

        infoBean.name = if (TextUtils.isEmpty(et_name.text.toString())) {
            ""
        } else {
            et_name.text.toString()
        }

        infoBean.sex = if (TextUtils.isEmpty(et_sex.text.toString())) {
            ""
        } else {
            et_sex.text.toString()
        }

        if (infoBean.save())
            Toast.makeText(this, "保存信息成功", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "保存信息失败", Toast.LENGTH_SHORT).show()
    }
}
