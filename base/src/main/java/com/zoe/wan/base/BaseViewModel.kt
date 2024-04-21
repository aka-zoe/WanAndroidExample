package com.zoe.wan.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.http.ExceptionCatchUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * ViewModel扩展方法：启动协程并且捕捉异常并处理
     * @param block         协程逻辑
     * @param onError       异常信息回调
     * @param onComplete    完成回调方法
     */
    fun ViewModel.launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        viewModelScope.launch(CoroutineExceptionHandler { context, throwable ->

            run {
                ExceptionCatchUtils.catchError(throwable)
                onError(throwable)
            }

        }) {
            try {
                block.invoke(this)
            } finally {
                onComplete()
            }
        }

    }

}
