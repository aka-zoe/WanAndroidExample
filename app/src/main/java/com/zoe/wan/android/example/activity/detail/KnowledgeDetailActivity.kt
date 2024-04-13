package com.zoe.wan.android.example.activity.detail

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.ActivityKnowledgeDetailBinding
import com.zoe.wan.android.example.databinding.ItemTabBinding
import com.zoe.wan.android.example.repository.data.DetailIntentData
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter

class KnowledgeDetailActivity : BaseActivity<ActivityKnowledgeDetailBinding,
    KnowledgeDetailViewModel>() {

    companion object {
        const val INTENT_TAB_DATA = "INTENT_TAB_DATA"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_knowledge_detail
    }

    override fun getViewModelId(): Int {
        return BR.detailVm
    }

    override fun initViewData() {
        val tabData: DetailIntentData? = intent.getSerializableExtra(INTENT_TAB_DATA) as
            DetailIntentData?
        if (tabData?.tabList.isNullOrEmpty()) {
            return
        }
        //初始化pager2适配器
        val pages = mutableListOf<Fragment>()
        val tabTitles = mutableListOf<String>()
        tabData?.tabList?.forEach {
            pages.add(FragDetailList(it?.id ?: ""))
            tabTitles.add(it?.name ?: "")
        }

        val pager2Adapter = Pager2Adapter(this@KnowledgeDetailActivity)
        pager2Adapter.setData(pages)

        binding?.detailViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.detailViewPager2?.adapter = pager2Adapter

        if (binding?.detailTabLayout == null || binding?.detailViewPager2 == null) {
            return
        }
        //tabLayout与viewPager2绑定
        TabLayoutMediator(
            binding?.detailTabLayout!!,
            binding?.detailViewPager2!!,
            true,
            true
        ) { tab, index ->
            val tabItemBinding = DataBindingUtil.inflate<ItemTabBinding>(
                layoutInflater, R.layout
                    .item_tab, null, false
            )
            tabItemBinding.tabItemTitle.text = tabTitles[index]
            tab.customView = tabItemBinding.root
        }.attach()

    }


}
