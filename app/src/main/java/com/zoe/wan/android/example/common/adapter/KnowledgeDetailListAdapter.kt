package com.zoe.wan.android.example.common.adapter

import android.view.ViewGroup
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemKnowledgeDetailListBinding
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleData
import com.zoe.wan.base.adapter.BaseAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 知识体系下的文章列表适配器
 */
class KnowledgeDetailListAdapter :
    BaseAdapter<KnowledgeDetailArticleData?, KnowledgeDetailListAdapter
    .KnowledgeDetailItemViewHolder>() {

    class KnowledgeDetailItemViewHolder(itemBinding: ItemKnowledgeDetailListBinding) :
        BaseViewHolder<ItemKnowledgeDetailListBinding>(itemBinding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): KnowledgeDetailItemViewHolder {
        return KnowledgeDetailItemViewHolder(
            getBinding(
                parent,
                R.layout.item_knowledge_detail_list
            )
        )
    }

    override fun bindHolder(holder: KnowledgeDetailItemViewHolder, position: Int) {
        val item = getDataList()?.get(position)
        holder.binding.item = item
        val name = if (item?.author?.isNotEmpty() == true) {
            item.author
        } else {
            item?.shareUser ?: ""
        }

        holder.binding.detailListItemName.text = name
    }

}
