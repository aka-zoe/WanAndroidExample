package com.zoe.wan.android.example.fragment.personal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.about_us.AboutUsActivity
import com.zoe.wan.android.example.activity.login.LoginActivity
import com.zoe.wan.android.example.activity.my_collects.MyCollectListActivity
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
        //我的收藏
        binding?.personalCollect?.setOnClickListener {
            startToActivity(MyCollectListActivity::class.java, false)
        }
        //关于我们
        binding?.personalAboutUs?.setOnClickListener {
            startToActivity(AboutUsActivity::class.java, false)
        }

        //检查更新
        binding?.personalCheckUpdate?.setOnClickListener {
            viewModel?.checkAppUpdate()
        }

        viewModel?.hasNewVersion?.observe(viewLifecycleOwner) {
            if (it == true) {
                //有新版本，弹窗提示更新
                showUpdateDialog()
            }
        }
    }

    private fun showUpdateDialog() {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder.setTitle("版本更新")
            dialogBuilder.setMessage("检查到有新版本，确定更新吗？")
            dialogBuilder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //确定按钮事件
                    jumpOutsideToDownload()
                }
            })

            dialogBuilder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //取消按钮事件
                    dialog?.dismiss()
                }
            })
            //弹窗显示
            dialogBuilder.create().show()
        }

    }


    private fun jumpOutsideToDownload() {
        val downloadUrl = viewModel?.downloadUrl?.get() ?: ""
        val uri = Uri.parse(downloadUrl)
        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        intent.setData(uri)

        context?.let {
//            val componentName = intent.resolveActivity(it.packageManager)
//            if (componentName != null) {
            it.startActivity(Intent.createChooser(intent, "请选择浏览器！"))
//            }
        }
    }

    /**
     * 非登录状态跳转到登录页
     */
    private fun shouldLogin() {

        if (viewModel?.showLogoutBtn?.get() == true) {
            return
        }
        startToActivity(LoginActivity::class.java, true)
    }

    private fun startToActivity(clazz: Class<*>, isLogin: Boolean) {
        val intent = Intent(context, clazz)
        if (isLogin) {
            intent.putExtra(LoginActivity.Intent_Type_Name, LoginActivity.Intent_Type_Value)
        }
        startActivity(intent)
    }

}
