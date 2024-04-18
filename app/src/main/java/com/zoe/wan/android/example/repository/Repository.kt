package com.zoe.wan.android.example.repository

import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.activity.login.LoginActivity
import com.zoe.wan.android.example.repository.data.CommonWebsiteListData
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.HomeTopListData
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleListData
import com.zoe.wan.android.example.repository.data.KnowledgeListData
import com.zoe.wan.android.example.repository.data.SearchHotKeyListData
import com.zoe.wan.android.example.repository.data.SearchResultListData
import com.zoe.wan.android.example.repository.data.UserData
import com.zoe.wan.android.http.BaseResponse
import com.zoe.wan.android.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

object Repository {

    private const val SUCCESS_CODE = 0
    private const val NEED_LOGIN_CODE = -1001

    private var mContext: WeakReference<Context>? = null

    /**
     * 初始化
     */
    fun init(context: Context) {
        mContext = WeakReference(context)
    }

    /**
     * 获取首页数据
     */
    suspend fun getHomeList(pageCount: String): HomeListData? {
        val data: BaseResponse<HomeListData?>? = getDefault().homeList(pageCount)
        return responseCall(data)
    }

    /**
     * 获取首页置顶数据
     */
    suspend fun getHomeTopList(): HomeTopListData? {
        val data: BaseResponse<HomeTopListData?>? = getDefault().topHomeList()
        return responseCall(data)
    }

    /**
     * 获取首页banner数据
     */
    suspend fun homeBanner(): HomeBannerData? {
        val data: BaseResponse<HomeBannerData?>? = getDefault().homeBanner()
        return responseCall(data)
    }

    /**
     * 登录
     */
    suspend fun login(username: String, password: String): UserData? {
        val data = getDefault().login(username, password)
        return responseCall(data)
    }

    /**
     * 注册
     */
    suspend fun register(username: String, password: String, repassword: String): UserData? {
        val data = getDefault().register(username, password, repassword)
        return responseCall(data)
    }


    /**
     * 登出
     */
    suspend fun logout(): Boolean {
        val data = getDefault().logout()
        return responseNoDataCall(data)
    }

    /**
     * 点击收藏文章列表
     */
    suspend fun collect(id: String): Boolean {
        val data = getDefault().collect(id)
        return responseNoDataCall(data)
    }

    /**
     * 点击取消收藏文章列表
     */
    suspend fun cancelCollect(id: String): Boolean {
        val data = getDefault().cancelCollect(id)
        return responseNoDataCall(data)
    }


    /**
     * 知识体系数据
     */
    suspend fun knowledgeList(): KnowledgeListData? {
        val data = getDefault().knowledgeList()
        return responseCall(data)
    }

    /**
     * 知识体系下的文章列表
     */
    suspend fun knowledgeArticleList(
        pageCount: String = "0",
        cid: String
    ): KnowledgeDetailArticleListData? {
        val data = getDefault().knowledgeArticleList(pageCount, cid)
        return responseCall(data)
    }

    /**
     * 搜索热词
     */
    suspend fun searchHotKeyList(): SearchHotKeyListData? {
        val data = getDefault().searchHotKeyList()
        return responseCall(data)
    }

    /**
     * 常用网站
     */
    suspend fun commonWebsiteList(): CommonWebsiteListData? {
        val data = getDefault().commonWebsiteList()
        return responseCall(data)
    }


    /**
     * 搜索
     */
    suspend fun search(k: String?, pageCount: String = "0"): SearchResultListData? {
        val data = getDefault().search(k = k ?: "", pageCount = pageCount)
        return responseCall(data)
    }


    private fun responseNoDataCall(response: BaseResponse<Any?>?): Boolean {
        if (response == null) {
            showToast("请求异常！")
            return false
        }
        //请求正常，返回数据
        if (response.getErrCode() == SUCCESS_CODE) {
            return true
        } else if (response.getErrCode() == NEED_LOGIN_CODE) {
            //需要登录，跳转到登录页
            startToLogin()
            return false
        } else {
            showToast(response.getErrMsg())
            return false
        }
    }

    /**
     * 1、code=0，返回业务数据
     * 2、code=-1001 跳转到登录页
     */
    private fun <T> responseCall(response: BaseResponse<T?>?): T? {
        if (response == null) {
            showToast("请求异常！")
            return null
        }

        //请求正常，返回数据
        if (response.getErrCode() == SUCCESS_CODE) {
            return response.getData()
        } else if (response.getErrCode() == NEED_LOGIN_CODE) {
            //需要登录，跳转到登录页
            startToLogin()
            return null
        } else {
            showToast(response.getErrMsg())
            return null
        }
    }

    private fun startToLogin() {
        mContext?.get()?.let {
            val intent = Intent(it, LoginActivity::class.java)
            intent.putExtra(LoginActivity.Intent_Type_Name, LoginActivity.Intent_Type_Value)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.startActivity(intent)
        }
    }

    private fun showToast(msg: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            ToastUtils.showShort(msg ?: "请求异常！")
        }
    }

    private fun getDefault(): ApiService {
        return RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }
}
