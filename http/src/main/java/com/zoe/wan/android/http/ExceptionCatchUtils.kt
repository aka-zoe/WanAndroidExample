package com.zoe.wan.android.http

import android.accounts.NetworkErrorException
import android.util.MalformedJsonException
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionCatchUtils {
    fun catchError(e:Throwable){
        e.printStackTrace()
        when(e){
            is HttpException -> {
                ToastUtils.showShort("服务器异常")
            }

            is SocketTimeoutException -> {
                ToastUtils.showShort("连接超时")
            }

            is UnknownHostException, is NetworkErrorException -> {
                ToastUtils.showShort("网络异常")
            }

            is MalformedJsonException, is JsonSyntaxException -> {
                ToastUtils.showShort("数据异常")
            }

            is InterruptedIOException -> {
                ToastUtils.showShort("服务器连接失败，请稍后重试")
            }
            // 自定义接口异常
            is ApiException -> {
                ToastUtils.showShort(e.message ?: "", e.getCode())
            }

            is ConnectException -> {
                ToastUtils.showShort("连接服务器失败")
            }

            else -> {
                ToastUtils.showShort("请求异常")
            }
        }
    }
}
