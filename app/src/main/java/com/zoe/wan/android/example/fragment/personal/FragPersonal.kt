package com.zoe.wan.android.example.fragment.personal

import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.Constants
import com.zoe.wan.android.example.activity.login.LoginActivity
import com.zoe.wan.android.example.databinding.FragmentPersonalBinding
import com.zoe.wan.base.BaseFragment

class FragPersonal : BaseFragment<FragmentPersonalBinding, FragPersonalViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_personal
    }

    override fun getViewModelId(): Int {
        return BR.personalVm
    }

    override fun initViewData() {

        val username = SPUtils.getInstance().getString(Constants.SP_USER_NAME)
        if(username.isNullOrEmpty()){
            binding?.personalTv?.text = "去登录"
        }else{
            binding?.personalTv?.text = "已登录"
        }

        binding?.personalTv?.setOnClickListener {

            val intent = Intent(context, LoginActivity::class.java)
            if(username.isNullOrEmpty()){
                intent.putExtra(LoginActivity.Intent_Type_Name, LoginActivity.Intent_Type_Value)
            }
            startActivity(intent)
        }
    }

}
