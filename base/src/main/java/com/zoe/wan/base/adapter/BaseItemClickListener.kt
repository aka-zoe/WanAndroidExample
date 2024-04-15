package com.zoe.wan.base.adapter

/**
 * 列表的通用点击方法回调
 */
abstract class BaseItemClickListener<T> {
    abstract fun itemClick(item: T?, position: Int)
}
