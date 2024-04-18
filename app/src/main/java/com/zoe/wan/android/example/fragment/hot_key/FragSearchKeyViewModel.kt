package com.zoe.wan.android.example.fragment.hot_key

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.CommonSearchData
import com.zoe.wan.android.example.repository.data.CommonWebsiteListData
import com.zoe.wan.android.example.repository.data.SearchHotKeyListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import com.zoe.wan.base.loading.LoadingUtils
import kotlinx.coroutines.launch

class FragSearchKeyViewModel(application: Application) : BaseViewModel(application) {

    val searchKeyList = SingleLiveEvent<List<CommonSearchData?>?>()
    val commonWebsiteList = SingleLiveEvent<List<CommonSearchData?>?>()

    init {
        LoadingUtils.showLoading()
        searchHotKeyList{
            commonWebsiteList{
                LoadingUtils.dismiss()
            }
        }

    }

    /**
     * 搜索热词
     */
    fun searchHotKeyList(callback:()->Unit) {
        viewModelScope.launch {
            val data: SearchHotKeyListData? = Repository.searchHotKeyList()
            if (!data.isNullOrEmpty()) {
                val list = mutableListOf<CommonSearchData?>()
                data.forEach {
                    list.add(CommonSearchData(it?.name, it?.link))
                }
                searchKeyList.postValue(list)
            }
            callback.invoke()
        }
    }

    /**
     * 常用网站
     */
    fun commonWebsiteList(callback:()->Unit) {
        viewModelScope.launch {
            val data: CommonWebsiteListData? = Repository.commonWebsiteList()
            if (!data.isNullOrEmpty()) {
                val list = mutableListOf<CommonSearchData?>()
                data.forEach {
                    list.add(CommonSearchData(it?.name, it?.link))
                }
                commonWebsiteList.postValue(list)
            }
            callback.invoke()
        }

    }

}
