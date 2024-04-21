package com.zoe.wan.android.example.activity.login

import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.http.HttpConstants
import com.zoe.wan.base.BaseViewModel
import java.util.UUID

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val usernameInput = ObservableField<String>()
    val passwordInput = ObservableField<String>()
    val passwordTwiceInput = ObservableField<String>()


    fun login(callback: (username: String) -> Unit) {
        val name = usernameInput.get()
        val pwd = passwordInput.get()
        if (name.isNullOrEmpty() || pwd.isNullOrEmpty()) {
            ToastUtils.showShort("输入不能为空！")
            return
        }
        launch({
            val data = Repository.login(name, pwd)
            val uuid = UUID.randomUUID().toString()
            LogUtils.d("我的token uuid=$uuid")
            SPUtils.getInstance().put(HttpConstants.SP_TOKEN, uuid)
            if (data != null) {
                callback.invoke(data.username ?: "")
            }
        })
    }

    fun register(callback: (username: String) -> Unit) {
        val name = usernameInput.get()
        val pwd = passwordInput.get()
        val repwd = passwordTwiceInput.get()
        if (name.isNullOrEmpty() || pwd.isNullOrEmpty() || repwd.isNullOrEmpty()) {
            ToastUtils.showShort("输入不能为空！")
            return
        }

        launch({
            val data = Repository.register(name, pwd, repwd)
            if (data != null) {
                callback.invoke(data.username ?: "")
            }
        })
    }
}
