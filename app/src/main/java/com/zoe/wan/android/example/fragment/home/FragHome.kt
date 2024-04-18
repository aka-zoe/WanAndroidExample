package com.zoe.wan.android.example.fragment.home

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.activity.webview.WebActivity
import com.zoe.wan.android.example.common.adapter.HomeListAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.BaseItemClickListener

class FragHome : BaseFragment<FragmentHomeBinding, FragHomeViewModel>() {
    private val adapter = HomeListAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {
        initListView()


        observerData()

        refreshOrLoadMore()
    }

    private fun refreshOrLoadMore() {
        //下拉刷新
        binding?.homeRefreshView?.setOnRefreshListener {
            viewModel?.initData(false){
                it.finishRefresh()
            }

        }

        //上拉加载
        binding?.homeRefreshView?.setOnLoadMoreListener {
            viewModel?.initData(true){
                it.finishLoadMore()
            }
        }
    }

    private fun observerData() {
        viewModel?.list?.observe(viewLifecycleOwner) { list ->
            adapter.setDataList(list)

        }

        viewModel?.bannerData?.observe(viewLifecycleOwner) { data ->

            adapter.setBannerData(data)

        }

    }

    private fun initListView() {



        binding?.homeListView?.layoutManager = LinearLayoutManager(context)
        binding?.homeListView?.adapter = adapter
        //item点击回调
        adapter.registerItemClickListener(object: BaseItemClickListener<HomeListItemData?>(){
            override fun itemClick(item: HomeListItemData?, position: Int) {
                jumpToWeb(item?.title, item?.link)
            }

        })
        adapter.registerHomeItemListener(object : HomeListAdapter.HomeItemClickListener {
            override fun itemCollect(id: String, position: Int, collect: Boolean) {
                if (collect) {
                    //取消收藏
                    viewModel?.cancelCollect(id) {
                        //收藏成功，此方法回调，更改图标
                        adapter.setCollect(false, position)
                    }
                } else {
                    //调用收藏接口
                    viewModel?.collect(id) {
                        //收藏成功，此方法回调，更改图标
                        adapter.setCollect(true, position)
                    }
                }

            }

            override fun bannerClick(title: String?, link: String?) {
                jumpToWeb(title, link)
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
