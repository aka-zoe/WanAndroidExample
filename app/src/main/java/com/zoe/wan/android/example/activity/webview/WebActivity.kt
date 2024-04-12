package com.zoe.wan.android.example.activity.webview

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.ActivityWebViewBinding
import com.zoe.wan.base.BaseActivity

class WebActivity : BaseActivity<ActivityWebViewBinding, WebViewModel>() {

    companion object {
        const val INTENT_WEB_TITLE_KEY = "INTENT_WEB_TITLE_KEY"
        const val INTENT_WEB_URL_KEY = "INTENT_WEB_URL_KEY"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun getViewModelId(): Int {
        return BR.webVm
    }

    override fun initViewData() {
        val title = intent.getStringExtra(INTENT_WEB_TITLE_KEY)
        val url = intent.getStringExtra(INTENT_WEB_URL_KEY)

        viewModel?.webTitle?.set(title)

        binding?.webViewBack?.setOnClickListener {
            finish()
        }

        binding?.webView?.loadUrl(url ?: "")
        initWebViewSettings()


    }

    private fun initWebViewSettings() {
        binding?.webView?.settings?.apply {
            //设置支持js脚本
            this.javaScriptEnabled = true
            //设置屏幕自适应
            this.useWideViewPort = true
            this.loadWithOverviewMode = true
            //缩放操作
            this.setSupportZoom(true)
            //设置内置的缩放控件，若为false则不可缩放
            this.builtInZoomControls = true
            //隐藏原生缩放控件
            this.displayZoomControls = false

            //其它细节
            //关闭webview中的缓存
            this.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            //可以访问文件
            this.allowFileAccess = true
            //支持通过js打开新的窗口
            this.javaScriptCanOpenWindowsAutomatically = true
            //支持自动加载图片
            this.loadsImagesAutomatically = true
            //设置编码格式
            this.defaultTextEncodingName = "utf-8"

        }

        //设置只能内部浏览器打开链接
        binding?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }
    }

}
