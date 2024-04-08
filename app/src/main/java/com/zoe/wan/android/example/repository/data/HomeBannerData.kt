package com.zoe.wan.android.example.repository.data

/**
 * banner数据返回实体
 */
class HomeBannerData : ArrayList<HomeBannerDataItem>()

data class HomeBannerDataItem(
    val desc: String?,
    val id: Int?,
    //图片链接
    val imagePath: String?,
    val isVisible: Int?,
    val order: Int?,
    //标题
    val title: String?,
    val type: Int?,
    val url: String?
)
