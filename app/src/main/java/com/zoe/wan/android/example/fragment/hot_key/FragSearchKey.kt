package com.zoe.wan.android.example.fragment.hot_key

import android.content.Intent
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.search.SearchActivity
import com.zoe.wan.android.example.activity.webview.WebActivity
import com.zoe.wan.android.example.common.adapter.CommonSearchGridAdapter
import com.zoe.wan.android.example.databinding.FragmentSearchKeyBinding
import com.zoe.wan.android.example.repository.data.CommonSearchData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.BaseItemClickListener
import com.zoe.wan.base.view.NoScrollLayoutManager

class FragSearchKey : BaseFragment<FragmentSearchKeyBinding, FragSearchKeyViewModel>() {

    private val searchListAdapter = CommonSearchGridAdapter()
    private val commonListAdapter = CommonSearchGridAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_key
    }

    override fun getViewModelId(): Int {
        return BR.searchKeyVm
    }

    override fun initViewData() {
        initListView()

        viewModel?.searchKeyList?.observe(viewLifecycleOwner) {
            searchListAdapter.setDataList(it)
        }

        viewModel?.commonWebsiteList?.observe(viewLifecycleOwner) {
            commonListAdapter.setDataList(it)
        }
    }

    private fun initListView() {
        //搜索热点
        binding?.searchHotKeyListView?.layoutManager = NoScrollLayoutManager(context, 3)
        binding?.searchHotKeyListView?.adapter = searchListAdapter
        searchListAdapter.registerItemClickListener(object :
            BaseItemClickListener<CommonSearchData?>() {
            override fun itemClick(item: CommonSearchData?, position: Int) {
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra(SearchActivity.Intent_Keyword, item?.name)
                startActivity(intent)
            }

        })
        //常用网站
        binding?.commonWebsiteListView?.layoutManager = NoScrollLayoutManager(context, 3)
        binding?.commonWebsiteListView?.adapter = commonListAdapter
        commonListAdapter.registerItemClickListener(object :
            BaseItemClickListener<CommonSearchData?>() {
            override fun itemClick(item: CommonSearchData?, position: Int) {
                jumpToWeb(item?.name, item?.link)
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
