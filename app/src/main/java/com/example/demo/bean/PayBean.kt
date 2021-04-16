package com.example.demo.bean

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**

@author dengfeijie
@description: 字符实体类
@date : 2020/8/22 18:09
 */
@Parcelize
class PayBean(
    @SerializedName("sign")
    val sign: String,
    @SerializedName("prepay_id")
    val prepay_id: String,
    @SerializedName("partner_id")
    val partner_id: String,
    @SerializedName("app_id")
    val app_id: String,
    @SerializedName("package_value")
    val package_value: String,
    @SerializedName("time_stamp")
    val time_stamp: String,
    @SerializedName("nonce_str")
    val nonce_str: String
) : android.os.Parcelable {
}