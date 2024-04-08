package com.zoe.wan.android.example.fragment.knowledge

import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.FragmentKnowledgeBinding
import com.zoe.wan.base.BaseFragment

class FragKnowledge:BaseFragment<FragmentKnowledgeBinding,FragKnowledgeViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge
    }

    override fun getViewModelId(): Int {
        return BR.knowledgeVm
    }

    override fun initViewData() {

    }

}
