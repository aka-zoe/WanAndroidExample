package com.zoe.wan.android.example.activity.my_collects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.activity.webview.WebActivity
import com.zoe.wan.android.example.common.ui.AppBar
import com.zoe.wan.android.example.repository.data.MyCollectItemListData

class MyCollectListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCollectListScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MyCollectListScreen() {
        val viewModel: MyCollectListViewModel = viewModel()
        Column {
            AppBar(onBackClick = {
                finish()
            }, title = "我的收藏")
            CollectListView(viewModel)
        }

    }

    @Composable
    private fun CollectListView(viewModel: MyCollectListViewModel) {
        val collects = viewModel.collectList.observeAsState()

        LazyColumn {
            items(count = collects.value?.size ?: 0) { index ->
                val item = collects.value?.get(index)
                ListItem(item) {
                    jumpToWeb(baseContext, item?.title, item?.link)
                }
            }
        }
    }

    @Composable
    private fun ListItem(item: MyCollectItemListData?, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dp_10))
                .clickable {
                    onClick.invoke()
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //作者
                Text(text = "作者: ${item?.author ?: "无"}")
                //分享时间
                Text(text = "时间: ${item?.niceDate}")
            }
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dp_5))
            )
            //标题
            Text(text = item?.title ?: "", fontSize = 16.sp)
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dp_5))
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //分类
                Text(text = "分类: ${item?.chapterName}")
                //分享事件
                Image(
                    painter = painterResource(id = R.drawable.img_collect),
                    contentDescription = "",
                    modifier = Modifier.size(
                        width = dimensionResource(id = R.dimen.dp_28),
                        height = dimensionResource(id = R.dimen.dp_28)
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dp_5))
            )
            //分割线
            Spacer(
                modifier = Modifier
                    .background(Color.Gray)
                    .height(dimensionResource(id = R.dimen.dp_0_5))
                    .fillMaxWidth()
            )
        }
    }

    private fun jumpToWeb(context: Context, title: String?, link: String?) {
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra(WebActivity.INTENT_WEB_TITLE_KEY, title)
        intent.putExtra(WebActivity.INTENT_WEB_URL_KEY, link)
        startActivity(intent)
    }
}
