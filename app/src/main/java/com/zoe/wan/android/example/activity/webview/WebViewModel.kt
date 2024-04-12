package com.zoe.wan.android.example.activity.webview

import android.app.Application
import androidx.databinding.ObservableField
import com.zoe.wan.base.BaseViewModel

class WebViewModel(application: Application) : BaseViewModel(application) {
    val webTitle = ObservableField<String>()

}
