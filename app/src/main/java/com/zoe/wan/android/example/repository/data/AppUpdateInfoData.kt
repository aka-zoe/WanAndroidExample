package com.zoe.wan.android.example.repository.data

/**
 * APP检查更新返回数据
 */
data class AppUpdateInfoData(
    val code: Int?,
    val `data`: AppUpdateInfo?,
    val message: String?
)

data class AppUpdateInfo(
    val appKey: String?,
    val appURl: String?,
    val buildBuildVersion: String?,
    val buildDescription: String?,
    val buildFileKey: String?,
    val buildFileSize: String?,
    val buildHaveNewVersion: Boolean?,
    val buildIcon: String?,
    val buildKey: String?,
    val buildName: String?,
    val buildUpdateDescription: String?,
    //版本号
    val buildVersion: String?,
    //版本code
    val buildVersionNo: String?,
    //APP下载链接
    val downloadURL: String?,
    val forceUpdateVersion: String?,
    val forceUpdateVersionNo: String?,
    val needForceUpdate: Boolean?
)
