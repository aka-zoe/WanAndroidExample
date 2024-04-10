package com.zoe.wan.android.example.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.common.adapter.HomeListAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.base.BaseFragment

class FragHome : BaseFragment<FragmentHomeBinding, FragHomeViewModel>() {
    private val adapter = HomeListAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {

        binding?.homeListView?.layoutManager = LinearLayoutManager(context)
        binding?.homeListView?.adapter = adapter

        viewModel?.list?.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
        }

        viewModel?.bannerData?.observe(viewLifecycleOwner) { data ->

            adapter.setBannerData(data)

        }

        //item点击回调
        adapter.registerItemListener(object : HomeListAdapter.ItemCollectListener {
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

        })
    }

}
