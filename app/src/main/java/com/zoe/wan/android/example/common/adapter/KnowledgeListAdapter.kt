package com.zoe.wan.android.example.common.adapter

import android.view.ViewGroup
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemKnowledgeListBinding
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import com.zoe.wan.base.adapter.BaseAdapter
import com.zoe.wan.base.adapter.BaseViewHolder
import java.lang.StringBuilder

/**
 * 知识体系列表适配器
 */
class KnowledgeListAdapter : BaseAdapter<KnowledgeListDataItem?, KnowledgeListAdapter
.KnowledgeItemViewHolder>() {

    class KnowledgeItemViewHolder(itemBinding: ItemKnowledgeListBinding) :
        BaseViewHolder<ItemKnowledgeListBinding>(itemBinding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): KnowledgeItemViewHolder {
        return KnowledgeItemViewHolder(getBinding(parent, R.layout.item_knowledge_list))
    }

    override fun bindHolder(holder: KnowledgeItemViewHolder, position: Int) {
        val item = getDataList()?.get(position)
        holder.binding.item = item

        val sb = StringBuilder()
        //展示子标题
        item?.children?.forEach { child ->
            sb.append("${child?.name}  ")
        }

        holder.binding.knowledgeItemSubTitle.text = sb.toString()
    }
}
