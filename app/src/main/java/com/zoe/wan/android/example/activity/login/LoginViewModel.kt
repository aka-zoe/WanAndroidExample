package com.zoe.wan.android.example.activity.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.base.BaseViewModel
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            val data = Repository.login(name, pwd)
            if (data != null) {
                callback.invoke(data.username ?: "")
            }

        }
    }

    fun register(callback: (username: String) -> Unit) {
        val name = usernameInput.get()
        val pwd = passwordInput.get()
        val repwd = passwordTwiceInput.get()
        if (name.isNullOrEmpty() || pwd.isNullOrEmpty() || repwd.isNullOrEmpty()) {
            ToastUtils.showShort("输入不能为空！")
            return
        }

        viewModelScope.launch {
            val data = Repository.register(name, pwd, repwd)
            if (data != null) {
                callback.invoke(data.username ?: "")
            }
        }
    }
}
