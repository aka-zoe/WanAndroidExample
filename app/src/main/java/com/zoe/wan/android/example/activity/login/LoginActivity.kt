package com.zoe.wan.android.example.activity.login

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.Constants
import com.zoe.wan.android.example.activity.tab.TabActivity
import com.zoe.wan.android.example.databinding.ActivityLoginBinding
import com.zoe.wan.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        //0表示登录，非0就是注册
        const val Intent_Type_Value = 0
        const val Intent_Type_Name = "Intent_Type_Name"
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelId(): Int {
        return BR.loginVm
    }

    override fun initViewData() {
        val type = intent.getIntExtra(Intent_Type_Name, -1)

        //当前是登录
        if (type == Intent_Type_Value) {
            //输入框隐藏
            binding?.inputPasswordTwice?.visibility = View.GONE
            binding?.registerButton?.visibility = View.VISIBLE
            binding?.loginOrRegisterBtn?.text = "确认登录"
        } else {
            //输入框隐藏
            binding?.inputPasswordTwice?.visibility = View.VISIBLE
            binding?.registerButton?.visibility = View.GONE
            binding?.loginOrRegisterBtn?.text = "确定注册"
        }

        binding?.loginOrRegisterBtn?.setOnClickListener {
            if (type == Intent_Type_Value) {
                //登录
                login()

            } else {
                //注册
                register()
            }
        }

        //进入注册页面
        binding?.registerButton?.setOnClickListener {
            startIntent(LoginActivity::class.java, false)
        }
    }

    /**
     * 登录
     */
    private fun login() {
        //登录成功后保存用户信息并跳转到首页
//        val sp = getSharedPreferences("wanAndroidSP", Context.MODE_PRIVATE)
        viewModel?.login { username ->
//            sp.edit().putString(Constants.SP_USER_NAME, username).commit()
            SPUtils.getInstance().put(Constants.SP_USER_NAME, username)
            LogUtils.d("SP存值 ${SPUtils.getInstance().getString(Constants.SP_USER_NAME, "")}")
            startIntent(TabActivity::class.java, false)
        }

    }

    /**
     * 注册
     */
    private fun register() {
        //注册成功后跳转到登录页开始登录
        viewModel?.register { username ->
            startIntent(LoginActivity::class.java, true)
        }
    }

    private fun startIntent(clazz: Class<*>, hasIntent: Boolean) {
        finish()
        val intent = Intent(this, clazz)
        if (hasIntent) {
            intent.putExtra(Intent_Type_Name, Intent_Type_Value)
        }
        startActivity(intent)
    }

}
