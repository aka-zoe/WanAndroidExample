package com.zoe.wan.android.example.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemKnowledgeDetailListBinding
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleData

/**
 * 知识体系下的文章列表适配器
 */
class KnowledgeDetailListAdapter :
    RecyclerView.Adapter<KnowledgeDetailListAdapter.KnowledgeDetailItemViewHolder>() {
    private var list: List<KnowledgeDetailArticleData?>? = null
    private var itemClickListener: KnowledgeItemClickListener? = null

    class KnowledgeDetailItemViewHolder(itemBinding: ItemKnowledgeDetailListBinding) : RecyclerView
    .ViewHolder
        (itemBinding.root) {
        var binding: ItemKnowledgeDetailListBinding

        init {
            binding = itemBinding
        }
    }

    interface KnowledgeItemClickListener {

        fun itemClick(item: KnowledgeDetailArticleData?)

    }

    fun registerItemListener(listener: KnowledgeItemClickListener?) {
        this.itemClickListener = listener
    }

    fun setDataList(dataList: List<KnowledgeDetailArticleData?>?) {
        if (dataList?.isNotEmpty() == true) {
            list = dataList
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KnowledgeDetailItemViewHolder {
        return KnowledgeDetailItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_knowledge_detail_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: KnowledgeDetailItemViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item

        val name = if (item?.author?.isNotEmpty() == true) {
            item.author
        } else {
            item?.shareUser ?: ""
        }

        holder.binding.detailListItemName.text = name
        holder.binding.root.setOnClickListener {
            itemClickListener?.itemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}
