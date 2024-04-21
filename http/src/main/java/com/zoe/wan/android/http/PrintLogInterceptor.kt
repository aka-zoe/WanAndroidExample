package com.zoe.wan.android.http

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

class PrintLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = chain.request().headers()
//       val response =  chain.proceed(chain.request())
//        response.body()?.string()
        LogUtils.d("PrintLogInterceptor headers=$headers")
        return chain.proceed(chain.request())
    }
}
