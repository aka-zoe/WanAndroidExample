package com.zoe.wan.android.example.fragment.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class FragHomeViewModel(application: Application) : BaseViewModel(application) {

    val list = SingleLiveEvent<List<HomeListItemData?>?>()
    val bannerData = SingleLiveEvent<HomeBannerData?>()

    init {
        getTopHomeList { topList ->
            getHomeList(topList)
        }

        getHomeBanner()
    }

    private fun getHomeList(topList: List<HomeListItemData?>?) {
        viewModelScope.launch {
            val data = Repository.getHomeList("0")
            if (data != null) {
                list.postValue((topList ?: emptyList()) + (data.datas ?: emptyList()))
            } else {
                list.postValue(topList)
            }
        }
    }

    private fun getTopHomeList(callback: (topList: List<HomeListItemData?>?) -> Unit) {
        viewModelScope.launch {
            val data = Repository.getHomeTopList()
            if (data != null) {
                callback.invoke(data)
            }else{
                callback.invoke(emptyList())
            }
        }
    }

    private fun getHomeBanner() {
        viewModelScope.launch {
            val data = Repository.homeBanner()
            if (data != null) {
                bannerData.postValue(data)
            }
        }
    }

    /**
     * 点击收藏文章列表
     */
    fun collect(id: String, callback: () -> Unit) {
        viewModelScope.launch {
            val success = Repository.collect(id)
            if (success) {
                callback.invoke()
            }
        }
    }


    /**
     * 点击取消收藏文章列表
     */
    fun cancelCollect(id: String, callback: () -> Unit) {
        viewModelScope.launch {
            val success = Repository.cancelCollect(id)
            if (success) {
                callback.invoke()
            }
        }
    }

}
