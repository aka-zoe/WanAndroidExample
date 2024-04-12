package com.zoe.wan.android.example.activity.detail

import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.FragmentDetailListBinding
import com.zoe.wan.base.BaseFragment

class FragDetailList:BaseFragment<FragmentDetailListBinding,FragDetailViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_list
    }

    override fun getViewModelId(): Int {
        return BR.fragDetailVm
    }

    override fun initViewData() {

    }
}
