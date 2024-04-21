package com.zoe.wan.android.example.activity.detail

import android.app.Application
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleData
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils

class FragDetailViewModel(application: Application) : BaseViewModel(application) {

    val list = SingleLiveEvent<List<KnowledgeDetailArticleData?>?>()
    var pageCount = 0


    fun knowledgeArticleList(loadMore: Boolean, cid: String, callback: () -> Unit) {
        LoadingUtils.showLoading()
        if (loadMore) {
            pageCount++
        } else {
            pageCount = 0
        }
        launch({
            val data: KnowledgeDetailArticleListData? = Repository.knowledgeArticleList(
                pageCount = "$pageCount",
                cid = cid
            )

            if (!data?.datas.isNullOrEmpty()) {
                if (loadMore) {
                    val newList = (list.value ?: emptyList()) + (data?.datas ?: emptyList())
                    list.postValue(newList)
                } else {
                    list.postValue(data?.datas)
                }

            } else {
                if (loadMore && pageCount > 0) {
                    pageCount--

                }
            }

        }, onComplete = {
            callback.invoke()
            LoadingUtils.dismiss()
        })
    }
}
