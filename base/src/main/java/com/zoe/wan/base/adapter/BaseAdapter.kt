package com.zoe.wan.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 通用的RecyclerView适配器
 * 泛型D：数据实体
 * 泛型V：ViewHolder 需要继承BaseViewHolder/RecyclerView.ViewHolder
 */
abstract class BaseAdapter<D, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {
    //通用的数据列表
    private var listData: List<D?>? = null
    //item点击回调
    private var listener: BaseItemClickListener<D>? = null

    /**
     * 设置数据
     */
    fun setDataList(list: List<D?>?) {
        list?.let {
            this.listData = it
            notifyDataSetChanged()
        }
    }

    /**
     * 注册点击回调
     */
    fun registerItemClickListener(listener: BaseItemClickListener<D>?) {
        listener?.let {
            this.listener = it
        }
    }

    /**
     * 拿到当前的列表数据
     */
    fun getDataList(): List<D?>? {
        return listData
    }

    /**
     * 获取ViewHolder，子类需要实现
     */
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): V

    /**
     * 通过getBinding方法可以拿到当前的itemBinding
     */
    fun <B : ViewDataBinding> getBinding(parent: ViewGroup, layoutId: Int): B {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    }

    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    /**
     * 子类需要实现，在里面写业务代码
     */
    abstract fun bindHolder(holder: V, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        //实现点击回调
        val item = getDataList()?.get(position)
        holder.itemView.setOnClickListener {
            listener?.itemClick(item, position)
        }
        //传递子类需要实现的方法
        bindHolder(holder, position)
    }


}

/**
 * 通用的ViewHolder
 * 泛型B：ItemDataBinding
 */
abstract class BaseViewHolder<B : ViewDataBinding>(b: B) : RecyclerView.ViewHolder(b.root) {
    var binding: B

    init {
        this.binding = b
    }
}
