package com.zoe.wan.android.example.fragment.personal

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.Constants
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.AppUpdateInfoData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils
import kotlinx.coroutines.launch

class FragPersonalViewModel(application: Application) : BaseViewModel(application) {

    val username = ObservableField<String>()
    val showLogoutBtn = ObservableField<Boolean>()

    //apk下载链接
    val downloadUrl = ObservableField<String>()

    //有新版本显示红色圆点
    val showNewVersionDot = ObservableField<Boolean>()

    //当前是否有新版本
    val hasNewVersion = SingleLiveEvent<Boolean>()

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

        //当前是否有新版本
        val newVersion = SPUtils.getInstance().getInt(Constants.SP_HAS_NEW_VERSION, -1)
        if (newVersion > 0 && newVersion > AppUtils.getAppVersionCode()) {
            showNewVersionDot.set(true)
        } else {
            showNewVersionDot.set(false)
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

    /**
     * 检查app新版本
     */
    fun checkAppUpdate() {
        launch({
            try {
                LoadingUtils.showLoading()
                val info: AppUpdateInfoData? = Repository.appCheckUpdate()
                //获取当前app的版本号
                val currentVersion = AppUtils.getAppVersionCode()
                //获取当前最新App版本
                val newVersion = (info?.data?.buildVersionNo ?: "0").toInt()
                if (currentVersion >= newVersion) {
                    //当前版本就是最新版本
                    hasNewVersion.postValue(false)
                    showNewVersionDot.set(false)
                } else {
                    //有新版本，提示更新
                    hasNewVersion.postValue(true)
                    showNewVersionDot.set(true)
                    downloadUrl.set(info?.data?.downloadURL ?: "")
                }
                //记住最新版本code
                SPUtils.getInstance().put(Constants.SP_HAS_NEW_VERSION, newVersion)
                LoadingUtils.dismiss()
            } catch (e: Exception) {
                hasNewVersion.postValue(false)
                showNewVersionDot.set(false)
                LoadingUtils.dismiss()
                LogUtils.d("检测新版本异常 checkAppUpdate error=$e")
            }

        }, onError = {
            LoadingUtils.dismiss()
        })
    }
}
