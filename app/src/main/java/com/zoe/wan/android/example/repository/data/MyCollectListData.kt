package com.zoe.wan.android.example.repository.data

/**
 * 我的收藏列表数据
 */
data class MyCollectListData(
    val curPage: Int?,
    val datas: List<MyCollectItemListData?>?,
    val offset: Int?,
    val over: Boolean?,
    val pageCount: Int?,
    val size: Int?,
    val total: Int?
)

data class MyCollectItemListData(
    val author: String?,
    val chapterId: Int?,
    val chapterName: String?,
    val courseId: Int?,
    val desc: String?,
    val envelopePic: String?,
    val id: Int?,
    val link: String?,
    val niceDate: String?,
    val origin: String?,
    val originId: Int?,
    val publishTime: Long?,
    val title: String?,
    val userId: Int?,
    val visible: Int?,
    val zan: Int?
)
