package com.zoe.wan.android.example.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemKnowledgeListBinding
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import java.lang.StringBuilder

/**
 * 知识体系列表适配器
 */
class KnowledgeListAdapter : RecyclerView.Adapter<KnowledgeListAdapter.KnowledgeItemViewHolder>() {
    private var list: List<KnowledgeListDataItem?>? = null
    private var itemClickListener: KnowledgeItemClickListener? = null

    class KnowledgeItemViewHolder(itemBinding: ItemKnowledgeListBinding) : RecyclerView.ViewHolder
        (itemBinding.root) {
        var binding: ItemKnowledgeListBinding

        init {
            binding = itemBinding
        }
    }

    interface KnowledgeItemClickListener {

        fun itemClick(item: KnowledgeListDataItem?)

    }

    fun registerItemListener(listener: KnowledgeItemClickListener?) {
        this.itemClickListener = listener
    }

    fun setDataList(dataList: List<KnowledgeListDataItem?>?) {
        if (dataList?.isNotEmpty() == true) {
            list = dataList
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeItemViewHolder {
        return KnowledgeItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent
                        .context
                ), R.layout.item_knowledge_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: KnowledgeItemViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item

        val sb = StringBuilder()
        //展示子标题
        item?.children?.forEach { child ->
            sb.append("${child?.name}  ")
        }

        holder.binding.knowledgeItemSubTitle.text = sb.toString()

        holder.binding.root.setOnClickListener {
            itemClickListener?.itemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}
