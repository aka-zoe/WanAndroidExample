package com.zoe.wan.android.example.fragment.personal

import android.content.Intent
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
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
        binding?.personalHead?.setOnClickListener { shouldLogin() }
        binding?.personalUsername?.setOnClickListener { shouldLogin() }

        //登出
        binding?.personalLogout?.setOnClickListener {
            viewModel?.logout()
        }
    }

    /**
     * 非登录状态跳转到登录页
     */
    private fun shouldLogin() {

        if (viewModel?.showLogoutBtn?.get() == true) {
            return
        }
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginActivity.Intent_Type_Name, LoginActivity.Intent_Type_Value)
        startActivity(intent)
    }

}
