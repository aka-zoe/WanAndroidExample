package com.zoe.wan.android.example.activity.search

import android.app.Application
import androidx.databinding.ObservableField
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.SearchResultData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils

class SearchViewModel(application: Application) : BaseViewModel(application) {

    val input = ObservableField<String>()
    val results = SingleLiveEvent<List<SearchResultData?>?>()

    fun search() {
        LoadingUtils.showLoading()
        launch({
            val data = Repository.search(k = input.get())
            if (data?.datas?.isNotEmpty() == true) {
                results.postValue(data.datas)
            } else {
                results.postValue(emptyList())
            }
            LoadingUtils.dismiss()
        }, onError = {
            results.postValue(emptyList())
            LoadingUtils.dismiss()
        })
    }
}
