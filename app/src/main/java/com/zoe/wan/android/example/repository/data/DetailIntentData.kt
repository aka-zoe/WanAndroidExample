package com.zoe.wan.android.example.repository.data

import java.io.Serializable

data class DetailIntentData(val tabList: List<DetailIntentItemData?>?) : Serializable

data class DetailIntentItemData(val id: String?, val name: String?) : Serializable
