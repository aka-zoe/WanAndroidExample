package com.zoe.wan.android.example.repository.data

/**
 * 搜索热点数据返回
 */
class SearchHotKeyListData : ArrayList<SearchHotKeyListDataItem?>()

data class SearchHotKeyListDataItem(
    val id: Int?,
    val link: String?,
    val name: String?,
    val order: Int?,
    val visible: Int?
)
