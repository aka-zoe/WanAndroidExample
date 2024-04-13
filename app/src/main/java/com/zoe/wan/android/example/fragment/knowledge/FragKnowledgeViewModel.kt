package com.zoe.wan.android.example.fragment.knowledge

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.KnowledgeListDataItem
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class FragKnowledgeViewModel(application: Application) : BaseViewModel(application) {

    val knowledgeList = SingleLiveEvent<List<KnowledgeListDataItem?>?>()

    init {
        knowledgeList{}
    }


    fun knowledgeList(callback: () -> Unit) {
        viewModelScope.launch {
            val data = Repository.knowledgeList()
            if (data?.isNotEmpty() == true) {
                knowledgeList.postValue(data)
            }
            callback.invoke()
        }

    }
}
