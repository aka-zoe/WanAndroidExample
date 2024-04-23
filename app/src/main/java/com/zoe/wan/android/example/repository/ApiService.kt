package com.zoe.wan.android.example.repository

import com.zoe.wan.android.example.Constants
import com.zoe.wan.android.example.repository.data.AppUpdateInfoData
import com.zoe.wan.android.example.repository.data.CommonWebsiteListData
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.HomeTopListData
import com.zoe.wan.android.example.repository.data.KnowledgeDetailArticleListData
import com.zoe.wan.android.example.repository.data.KnowledgeListData
import com.zoe.wan.android.example.repository.data.MyCollectListData
import com.zoe.wan.android.example.repository.data.SearchHotKeyListData
import com.zoe.wan.android.example.repository.data.SearchResultListData
import com.zoe.wan.android.example.repository.data.UserData
import com.zoe.wan.android.http.ApiAddress
import com.zoe.wan.android.http.ApiAddress.Article_List
import com.zoe.wan.android.http.ApiAddress.Collect
import com.zoe.wan.android.http.ApiAddress.Collect_Cancel
import com.zoe.wan.android.http.ApiAddress.Common_Use_Website
import com.zoe.wan.android.http.ApiAddress.Home_Banner
import com.zoe.wan.android.http.ApiAddress.Knowledge_List
import com.zoe.wan.android.http.ApiAddress.Knowledge_List_detail
import com.zoe.wan.android.http.ApiAddress.Login
import com.zoe.wan.android.http.ApiAddress.Logout
import com.zoe.wan.android.http.ApiAddress.My_Collect
import com.zoe.wan.android.http.ApiAddress.Register
import com.zoe.wan.android.http.ApiAddress.Search
import com.zoe.wan.android.http.ApiAddress.Search_Hot_Key
import com.zoe.wan.android.http.ApiAddress.Top_Article_List
import com.zoe.wan.android.http.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * 获取首页列表数据
     */
    @GET("$Article_List{pageCount}/json")
    suspend fun homeList(@Path("pageCount") pageCount: String): BaseResponse<HomeListData?>?

    /**
     * 获取首页置顶列表数据
     */
    @GET(Top_Article_List)
    suspend fun topHomeList(): BaseResponse<HomeTopListData?>?


    /**
     * 获取首页banner数据
     */
    @GET(Home_Banner)
    suspend fun homeBanner(): BaseResponse<HomeBannerData?>?


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(Login)
    suspend fun login(@Field("username") username: String, @Field("password") password: String)
        : BaseResponse<UserData?>?

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(Register)
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): BaseResponse<UserData?>?

    /**
     * 退出登录
     */
    @GET(Logout)
    suspend fun logout(): BaseResponse<Any?>?

    /**
     * 点击收藏文章列表
     */
    @POST("$Collect{id}/json")
    suspend fun collect(@Path("id") id: String): BaseResponse<Any?>?

    /**
     * 点击取消收藏文章列表
     */
    @POST("$Collect_Cancel{id}/json")
    suspend fun cancelCollect(@Path("id") id: String): BaseResponse<Any?>?

    /**
     * 知识体系数据
     */
    @GET(Knowledge_List)
    suspend fun knowledgeList(): BaseResponse<KnowledgeListData?>?

    /**
     * 知识体系下的文章列表
     */
    @GET("$Knowledge_List_detail{pageCount}/json")
    suspend fun knowledgeArticleList(
        @Path("pageCount") pageCount: String = "0",
        @Query("cid") cid: String
    ): BaseResponse<KnowledgeDetailArticleListData?>?

    /**
     * 搜索热词
     */
    @GET(Search_Hot_Key)
    suspend fun searchHotKeyList(): BaseResponse<SearchHotKeyListData?>?

    /**
     * 常用网站
     */
    @GET(Common_Use_Website)
    suspend fun commonWebsiteList(): BaseResponse<CommonWebsiteListData?>?

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("$Search/{pageCount}/json")
    suspend fun search(@Field("k") k: String, @Path("pageCount") pageCount: String = "0")
        : BaseResponse<SearchResultListData?>?


    /**
     * 我的收藏：文章列表
     */
    @GET("$My_Collect/{pageCount}/json")
    suspend fun myCollect(@Path("pageCount") pageCount: String): BaseResponse<MyCollectListData?>?


    /**
     * 检查APP新版本（蒲公英API，每天限额200次）
     */
    @POST(ApiAddress.APP_UPDATE)
    @FormUrlEncoded
    suspend fun appUpdateInfo(
        @Field("_api_key") apiKey: String = Constants.PGY_API_KEY, @Field
            ("appKey") appley: String = Constants.PGY_APP_KEY
    ): AppUpdateInfoData?
}
