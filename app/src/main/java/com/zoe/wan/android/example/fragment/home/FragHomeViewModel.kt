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

    val list = SingleLiveEvent<List<HomeListItemData>?>()
    val bannerData = SingleLiveEvent<HomeBannerData?>()

    init {
        getHomeList()
        getHomeBanner()
    }

    private fun getHomeList() {
        viewModelScope.launch {
            val data = Repository.getHomeList("0")
            if (data != null) {
                list.postValue(data.datas)
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
