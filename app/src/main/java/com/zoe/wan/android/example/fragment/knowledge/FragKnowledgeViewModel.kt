package com.zoe.wan.android.example.fragment.knowledge

import android.app.Application
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils

class FragKnowledgeViewModel(application: Application) : BaseViewModel(application) {

    val knowledgeList = SingleLiveEvent<List<KnowledgeListDataItem?>?>()

    init {
        knowledgeList {}
    }


    fun knowledgeList(callback: () -> Unit) {
        LoadingUtils.showLoading()
        launch({
            val data = Repository.knowledgeList()
            if (data?.isNotEmpty() == true) {
                knowledgeList.postValue(data)
            }
        }, onComplete = {
            callback.invoke()
            LoadingUtils.dismiss()
        })

    }
}
