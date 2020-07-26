package com.example.demo.bean


import com.google.gson.annotations.SerializedName

data class NewsBean(
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("stat")
        val stat: String
    ) {
        data class Data(
            @SerializedName("author_name")
            val authorName: String,
            @SerializedName("category")
            val category: String,
            @SerializedName("date")
            val date: String,
            @SerializedName("thumbnail_pic_s")
            val thumbnailPicS: String,
            @SerializedName("thumbnail_pic_s02")
            val thumbnailPicS02: String,
            @SerializedName("thumbnail_pic_s03")
            val thumbnailPicS03: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("uniquekey")
            val uniquekey: String,
            @SerializedName("url")
            val url: String
        )
    }
}