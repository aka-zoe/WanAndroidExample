package com.zoe.wan.android.example.activity.about_us

import android.app.Application
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.AppUtils
import com.zoe.wan.base.BaseViewModel

class AboutUsViewModel(application: Application) : BaseViewModel(application) {

    val version = ObservableField<String>()

    init {

        //获取版本号
        val vName = AppUtils.getAppVersionName()
        version.set(vName)

    }

}
