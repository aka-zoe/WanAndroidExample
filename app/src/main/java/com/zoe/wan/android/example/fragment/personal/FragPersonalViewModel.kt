package com.zoe.wan.android.example.fragment.personal

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.Constants
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.base.BaseViewModel
import kotlinx.coroutines.launch

class FragPersonalViewModel(application: Application) : BaseViewModel(application) {

    val username = ObservableField<String>()
    val showLogoutBtn = ObservableField<Boolean>()

    init {

        val name = SPUtils.getInstance().getString(Constants.SP_USER_NAME)

        if (name.isNullOrEmpty()) {
            //未登录
            username.set("未登录")
            showLogoutBtn.set(false)
        } else {
            //已登录
            username.set(name)
            showLogoutBtn.set(true)
        }

    }

    fun logout() {
        viewModelScope.launch {
            val success = Repository.logout()
            if (success) {
                //登出成功清理缓存，界面状态变化
                SPUtils.getInstance().clear()
                //未登录
                username.set("未登录")
                showLogoutBtn.set(false)

                ToastUtils.showShort("退出登录成功")
            } else {
                ToastUtils.showShort("退出登录失败")
            }
        }
    }
}
