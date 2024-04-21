package com.zoe.wan.android.example.activity.my_collects

import android.app.Application
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.MyCollectItemListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils

class MyCollectListViewModel(application: Application) : BaseViewModel(application) {

    val collectList = SingleLiveEvent<List<MyCollectItemListData?>?>()

    init {
        myCollectList()
    }

    fun myCollectList() {
        LoadingUtils.showLoading()
        launch({
            val data = Repository.myCollect("0")
            if (data?.datas?.isNotEmpty() == true) {
                collectList.postValue(data.datas)
            } else {
                collectList.postValue(emptyList())
            }
        }, onComplete = {
            LoadingUtils.dismiss()
        })
    }

}
