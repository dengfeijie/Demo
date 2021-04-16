package com.example.demo.bean


import com.google.gson.annotations.SerializedName

data class PayResponse(
    @SerializedName("app_id")
    val appId: String,
    @SerializedName("nonce_str")
    val nonceStr: String,
    @SerializedName("package_value")
    val packageValue: String,
    @SerializedName("partner_id")
    val partnerId: String,
    @SerializedName("prepay_id")
    val prepayId: String,
    @SerializedName("sign")
    val sign: String,
    @SerializedName("time_stamp")
    val timeStamp: String
)