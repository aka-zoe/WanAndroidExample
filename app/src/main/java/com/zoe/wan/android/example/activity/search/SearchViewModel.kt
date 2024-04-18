package com.zoe.wan.android.example.activity.search

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.SearchResultData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel(application) {

    val input = ObservableField<String>()
    val results = SingleLiveEvent<List<SearchResultData?>?>()

    fun search() {
        LoadingUtils.showLoading()
        viewModelScope.launch {
            val data = Repository.search(k = input.get())
            if (data?.datas?.isNotEmpty() == true) {
                results.postValue(data.datas)
            } else {
                results.postValue(emptyList())
            }
            LoadingUtils.dismiss()
        }
    }
}
