package com.zoe.wan.android.example.repository.data

/**
 * 常用网站列表返回
 */
class CommonWebsiteListData : ArrayList<CommonWebsiteListDataItem?>()

data class CommonWebsiteListDataItem(
    val category: String?,
    val icon: String?,
    val id: Int?,
    val link: String?,
    val name: String?,
    val order: Int?,
    val visible: Int?
)
