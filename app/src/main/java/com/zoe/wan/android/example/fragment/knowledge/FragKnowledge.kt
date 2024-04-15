package com.zoe.wan.android.example.fragment.knowledge

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.detail.KnowledgeDetailActivity
import com.zoe.wan.android.example.activity.detail.KnowledgeDetailActivity.Companion.INTENT_TAB_DATA
import com.zoe.wan.android.example.common.adapter.KnowledgeListAdapter
import com.zoe.wan.android.example.databinding.FragmentKnowledgeBinding
import com.zoe.wan.android.example.repository.data.DetailIntentData
import com.zoe.wan.android.example.repository.data.DetailIntentItemData
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.BaseItemClickListener

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

        binding?.knowledgeRefreshView?.setOnRefreshListener {
            viewModel?.knowledgeList {
                it.finishRefresh()
            }
        }
        viewModel?.knowledgeList?.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }

        adapter.registerItemClickListener(object : BaseItemClickListener<KnowledgeListDataItem?>() {

            override fun itemClick(item: KnowledgeListDataItem?, position: Int) {
                val tabList = mutableListOf<DetailIntentItemData?>()

                item?.children?.forEach {
                    tabList.add(DetailIntentItemData("${it?.id}", it?.name))
                }

                val tabData = DetailIntentData(tabList)

                val intent = Intent(context, KnowledgeDetailActivity::class.java)
                intent.putExtra(INTENT_TAB_DATA, tabData)
                startActivity(intent)
            }
        })
    }

}
