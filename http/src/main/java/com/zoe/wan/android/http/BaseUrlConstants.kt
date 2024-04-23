package com.zoe.wan.android.http

object BaseUrlConstants {
    private const val baseUrl: String = "https://www.wanandroid.com/"
    private const val BASE_URL_PGY: String = "https://www.pgyer.com/"

    fun getHost(): String {
        return baseUrl
    }

    fun getPgyHost(): String {
        return BASE_URL_PGY
    }

}
