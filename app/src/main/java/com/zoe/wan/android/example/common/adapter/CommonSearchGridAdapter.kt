package com.zoe.wan.android.example.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemCommonSearchKeyBinding
import com.zoe.wan.android.example.repository.data.CommonSearchData

class CommonSearchGridAdapter : RecyclerView.Adapter<CommonSearchGridAdapter
.CommonSearchViewHolder>() {

    private var list: List<CommonSearchData?>? = null
    private var listener: CommonSearchItemListener? = null

    class CommonSearchViewHolder(binding: ItemCommonSearchKeyBinding) : RecyclerView.ViewHolder
        (binding.root) {
        var binding: ItemCommonSearchKeyBinding

        init {
            this.binding = binding
        }
    }

    interface CommonSearchItemListener {
        fun itemClick(name: String?, link: String?)
    }

    fun setDataList(list: List<CommonSearchData?>?) {
        list?.let {
            this.list = it
            notifyDataSetChanged()
        }
    }

    fun registerItemListener(listener: CommonSearchItemListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonSearchViewHolder {
        return CommonSearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent
                        .context
                ), R.layout.item_common_search_key, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommonSearchViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item
        holder.binding.root.setOnClickListener {
            this.listener?.let {
                if (item?.link == null) {
                    item?.link = ""
                }
                it.itemClick(item?.name, item?.link)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}
