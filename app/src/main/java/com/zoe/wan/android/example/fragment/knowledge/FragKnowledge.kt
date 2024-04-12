package com.zoe.wan.android.example.fragment.knowledge

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.detail.KnowledgeDetailActivity
import com.zoe.wan.android.example.common.adapter.KnowledgeListAdapter
import com.zoe.wan.android.example.databinding.FragmentKnowledgeBinding
import com.zoe.wan.base.BaseFragment

class FragKnowledge : BaseFragment<FragmentKnowledgeBinding, FragKnowledgeViewModel>() {
    private val adapter = KnowledgeListAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge
    }

    override fun getViewModelId(): Int {
        return BR.knowledgeVm
    }

    override fun initViewData() {

        binding?.knowledgeListView?.layoutManager = LinearLayoutManager(context)
        binding?.knowledgeListView?.adapter = adapter

        viewModel?.knowledgeList?.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }

        adapter.registerItemListener(object : KnowledgeListAdapter.KnowledgeItemClickListener {
            override fun itemClick(position: Int) {
                val intent = Intent(context, KnowledgeDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

}
