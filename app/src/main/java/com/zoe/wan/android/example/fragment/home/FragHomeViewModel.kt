package com.zoe.wan.android.example.fragment.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils
import kotlinx.coroutines.launch

class FragHomeViewModel(application: Application) : BaseViewModel(application) {

    val list = SingleLiveEvent<List<HomeListItemData?>?>()
    val bannerData = SingleLiveEvent<HomeBannerData?>()
    var pageCount = 0

    init {

        initData(false){}
    }

    fun initData(loadMore: Boolean,callback: () -> Unit) {
        LoadingUtils.showLoading()
        if (!loadMore) {
            pageCount = 0
            getHomeBanner()
        } else {
            pageCount++
        }

        getTopHomeList(loadMore) { topList ->
            getHomeList(loadMore,topList){
                callback.invoke()
                LoadingUtils.dismiss()
            }
        }


    }

    private fun getHomeList(loadMore: Boolean, topList: List<HomeListItemData?>?,callback:()->Unit) {
        viewModelScope.launch {
            val data = Repository.getHomeList("$pageCount")

            if (data != null) {
                if (loadMore) {
                    val newList = (list.value ?: emptyList()) + (data?.datas ?: emptyList())
                    list.postValue(newList)
                } else {
                    list.postValue((topList ?: emptyList()) + (data.datas ?: emptyList()))
                }

            } else {
                if (loadMore && pageCount > 0) {
                    pageCount--
                }
                list.postValue(topList)
            }
            callback.invoke()
        }
    }

    private fun getTopHomeList(
        loadMore: Boolean,
        callback: (topList: List<HomeListItemData?>?) -> Unit
    ) {
        if (loadMore) {
            callback.invoke(emptyList())
        } else {
            viewModelScope.launch {
                val data = Repository.getHomeTopList()
                if (data != null) {
                    callback.invoke(data)
                } else {
                    callback.invoke(emptyList())
                }
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
