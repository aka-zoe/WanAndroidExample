package com.zoe.wan.android.example.common.adapter

import android.view.ViewGroup
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemCommonSearchKeyBinding
import com.zoe.wan.android.example.repository.data.CommonSearchData
import com.zoe.wan.base.adapter.BaseAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

class CommonSearchGridAdapter : BaseAdapter<CommonSearchData?, CommonSearchGridAdapter.CommonSearchViewHolder>() {
    class CommonSearchViewHolder(binding: ItemCommonSearchKeyBinding) : BaseViewHolder<ItemCommonSearchKeyBinding>
        (binding)
    override fun getViewHolder(parent: ViewGroup, viewType: Int): CommonSearchViewHolder {
       return CommonSearchViewHolder(getBinding(parent,R.layout.item_common_search_key))
    }

    override fun bindHolder(holder: CommonSearchViewHolder, position: Int) {
        val item = getDataList()?.get(position)
        holder.binding.item = item
    }

}
