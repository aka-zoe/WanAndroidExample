package com.zoe.wan.android.example.repository.data

/**
 * 知识体系数据返回
 */
class KnowledgeListData : ArrayList<KnowledgeListDataItem?>()


data class KnowledgeListDataItem(
    val articleList: List<Any?>?,
    val author: String?,
    //明细数据
    val children: List<Children?>?,
    val courseId: Int?,
    val cover: String?,
    val desc: String?,
    val id: Int?,
    val lisense: String?,
    val lisenseLink: String?,
    //标题
    val name: String?,
    val order: Int?,
    val parentChapterId: Int?,
    val type: Int?,
    val userControlSetTop: Boolean?,
    val visible: Int?
)

data class Children(
    val articleList: List<Article?>?,
    val author: String?,
    val children: List<Any?>?,
    val courseId: Int?,
    val cover: String?,
    val desc: String?,
    val id: Int?,
    val lisense: String?,
    val lisenseLink: String?,
    //子标题
    val name: String?,
    val order: Int?,
    val parentChapterId: Int?,
    val type: Int?,
    val userControlSetTop: Boolean?,
    val visible: Int?
)


data class Article(
    val adminAdd: Boolean?,
    val apkLink: String?,
    val audit: Int?,
    val author: String?,
    val canEdit: Boolean?,
    val chapterId: Int?,
    val chapterName: String?,
    val collect: Boolean?,
    val courseId: Int?,
    val desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean?,
    val host: String?,
    val id: Int?,
    val isAdminAdd: Boolean?,
    val link: String?,
    val niceDate: String?,
    val niceShareDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long?,
    val realSuperChapterId: Int?,
    val selfVisible: Int?,
    val shareDate: Long?,
    val shareUser: String?,
    val superChapterId: Int?,
    val superChapterName: String?,
    val tags: List<Any?>?,
    val title: String?,
    val type: Int?,
    val userId: Int?,
    val visible: Int?,
    val zan: Int?
)
