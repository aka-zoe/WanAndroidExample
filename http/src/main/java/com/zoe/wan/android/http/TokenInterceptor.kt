package com.zoe.wan.android.http

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * token拦截器：添加token到请求头中
 */
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        //登录接口不添加token
        if (request.url().url().path.contains("user/login")) {
            return chain.proceed(request)
        }

        request = request.newBuilder()
            .addHeader("token", SPUtils.getInstance().getString(HttpConstants.SP_TOKEN))
            .build()
        return chain.proceed(request)
    }
}
