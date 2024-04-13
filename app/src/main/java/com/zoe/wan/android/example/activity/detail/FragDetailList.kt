package com.zoe.wan.android.example.activity.detail

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.webview.WebActivity
import com.zoe.wan.android.example.common.adapter.KnowledgeDetailListAdapter
import com.zoe.wan.android.example.databinding.FragmentDetailListBinding
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleData
import com.zoe.wan.base.BaseFragment

/**
 * 展示知识体系下文章列表的
 */
class FragDetailList(private val id: String) : BaseFragment<FragmentDetailListBinding,
    FragDetailViewModel>() {
    private val adapter = KnowledgeDetailListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_list
    }

    override fun getViewModelId(): Int {
        return BR.fragDetailVm
    }

    override fun initViewData() {

        binding?.detailListView?.layoutManager = LinearLayoutManager(context)
        binding?.detailListView?.adapter = adapter

        //请求数据
        viewModel?.knowledgeArticleList(false, id) {}

        binding?.detailListRefreshView?.setOnRefreshListener {
            //请求数据
            viewModel?.knowledgeArticleList(false, id) {
                it.finishRefresh()
            }
        }

        binding?.detailListRefreshView?.setOnLoadMoreListener {
            //请求数据
            viewModel?.knowledgeArticleList(true, id) {
                it.finishLoadMore()
            }
        }

        viewModel?.list?.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }

        adapter.registerItemListener(object :
            KnowledgeDetailListAdapter.KnowledgeItemClickListener {
            override fun itemClick(item: KnowledgeDetailArticleData?) {
                jumpToWeb(item?.title, item?.link)
            }
        })
    }

    private fun jumpToWeb(title: String?, link: String?) {
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra(WebActivity.INTENT_WEB_TITLE_KEY, title)
        intent.putExtra(WebActivity.INTENT_WEB_URL_KEY, link)
        startActivity(intent)
    }

}
