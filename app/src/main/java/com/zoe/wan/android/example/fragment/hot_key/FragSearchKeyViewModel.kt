package com.zoe.wan.android.example.fragment.hot_key

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.CommonSearchData
import com.zoe.wan.android.example.repository.data.CommonWebsiteListData
import com.zoe.wan.android.example.repository.data.SearchHotKeyListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class FragSearchKeyViewModel(application: Application) : BaseViewModel(application) {

    val searchKeyList = SingleLiveEvent<List<CommonSearchData?>?>()
    val commonWebsiteList = SingleLiveEvent<List<CommonSearchData?>?>()

    init {
        searchHotKeyList()
        commonWebsiteList()
    }

    /**
     * 搜索热词
     */
    fun searchHotKeyList() {
        viewModelScope.launch {
            val data: SearchHotKeyListData? = Repository.searchHotKeyList()
            if (!data.isNullOrEmpty()) {
                val list = mutableListOf<CommonSearchData?>()
                data.forEach {
                    list.add(CommonSearchData(it?.name, it?.link))
                }
                searchKeyList.postValue(list)
            }
        }
    }

    /**
     * 常用网站
     */
    fun commonWebsiteList() {
        viewModelScope.launch {
            val data: CommonWebsiteListData? = Repository.commonWebsiteList()
            if (!data.isNullOrEmpty()) {
                val list = mutableListOf<CommonSearchData?>()
                data.forEach {
                    list.add(CommonSearchData(it?.name, it?.link))
                }
                commonWebsiteList.postValue(list)
            }
        }

    }

}
