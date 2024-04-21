package com.zoe.wan.base.loading

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import com.zoe.wan.base.R
import com.zoe.wan.base.databinding.LoadingDialogBinding

class LoadingDialog(private val context: Context) : Dialog(context) {
    private var binding: LoadingDialogBinding? = null
    private var animation: Animation? = null

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.loading_dialog,
            null, false
        )
        //点击其他区域，不需要关闭弹窗
        setCanceledOnTouchOutside(false)
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading_rotate)
        //匀速执行
        animation?.interpolator = LinearInterpolator()
        binding?.root?.let {
            setContentView(it)
        }

        window?.attributes?.gravity = Gravity.CENTER

    }

    fun showLoading() {
        animation?.let {
            binding?.loadingImage?.startAnimation(it)
        }
        super.show()
    }

    fun dialogShowing(): Boolean {
        return isShowing
    }

    fun dismissLoading() {
        animation?.cancel()
        animation = null
        super.dismiss()
    }
}
